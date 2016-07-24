# Datawire {{ cookiecutter.name }}"

{{ cookiecutter.description }}

# License

{%- if cookiecutter.license == "Apache Software License 2.0" -%}

Datawire {{ cookiecutter.name }} is open-source software licensed under **Apache 2.0**. Please see [LICENSE](LICENSE) for further details.

{%- elif cookiecutter.license == "Datawire Proprietary" -%}

Datawire {{ cookiecutter.name }} is closed-source software. Please see [LICENSE](LICENSE) for further details.

{% endif %}