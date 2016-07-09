#!/usr/bin/env bash
set -euxo pipefail

BIN_PATH="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)"
source ${BIN_PATH}/common.sh

# Command line option parsing

FLAG_RPM=no
FLAG_DEBIAN=no
FLAG_DOCKER=no

for i in "$@"; do
case "$i" in
#    -a=*|--arg=*)
#    ARG="${i#*=}"
#    ;;
    --deb)
    FLAG_DEBIAN=yes
    ;;
    --docker)
    FLAG_DOCKER=yes
    ;;
    --rpm)
    FLAG_DOCKER=yes
    ;;    
    *)

    ;;
esac
done

printf "$FLAG_DEBIAN"

# Update this to indicate what programs are required before the script can successfully run.
REQUIRED_PROGRAMS=(fpm deb-s3)

WORKSPACE_DIR="${WORKSPACE:?Jenkins \$WORKSPACE environment variable is not set}"
BUILD_TOOLS_DIR="${WORKSPACE_DIR}/build-tools"

QUARK_INSTALL_URL="https://raw.githubusercontent.com/datawire/quark/master/install.sh"
QUARK_BRANCH="rel/0.7.6"
QUARK_INSTALL_DIR="${BUILD_TOOLS_DIR}/quark"
QUARK_INSTALL_ARGS="-qqq -t ${QUARK_INSTALL_DIR} ${QUARK_BRANCH}"
QUARK_EXEC="${QUARK_INSTALL_DIR}/bin/quark"

VIRTUALENV="${BUILD_TOOLS_DIR}/virtualenv"

sanity_check "${REQUIRED_PROGRAMS[@]}"
mkdir -p ${BUILD_TOOLS_DIR}

header "Setup Python virtualenv"
set +u
virtualenv ${VIRTUALENV}
. ${VIRTUALENV}/bin/activate
set -u

if ! command -v quark >/dev/null 2>&1; then
    # TODO(FEATURE, Quark Installer):
    # The Quark installer should be modified so the $PATH test can be disabled if installing to a specific location.

    header "Setup Datawire Quark"
    curl -sL "$QUARK_INSTALL_URL" | bash -s -- ${QUARK_INSTALL_ARGS}
    . ${QUARK_INSTALL_DIR}/config.sh
    quark --version
fi

header "Build Service"
make clean all

header "Create OS packages and Docker images"

if [[ "$FLAG_DEBIAN" = "yes" ]]; then
    make deb
fi

if [[ "$FLAG_DOCKER" = "yes" ]]; then
    make docker
fi

header "Publishing packages and images"
make publish