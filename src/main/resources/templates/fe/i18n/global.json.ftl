{
    "global": {
        "title": "${projectNameCamelCase}",
        "browsehappy": "Stai utilizzando un browser <strong> non aggiornato </ strong> <a href=\"http://browsehappy.com/?locale=it\"> Aggiorna il tuo browser per </a> migliorare la tua esperienza.",
        "menu": {
            "home": "Home",
            "dashboard": {
                "main": "Dashboard",
                "barchart": "BarChart",
                "doughnutchart": "DoughnutChart",
                "linechart": "LineChart",
                "piechart": "PieChart",
                "polarareachart": "PolarAreaChart",
                "radarchart": "RadarChart"
            },
            "jhipster-needle-menu-add-element": "JH will add additional menu entries here (do not translate!)",
            "entities": {
<#list tables as table>
                "${table.classNameLowerCase}": "${table.entityName}",
</#list>
                "main": "Entità",
                "jhipster-needle-menu-add-entry": "JH will add additional entities here (do not translate!)"
            },
            "account": {
                "main": "Utente",
                "settings": "Impostazioni",
                "password": "Password",
                "sessions": "Sessioni",
                "login": "Accesso",
                "logout": "Esci",
                "register": "Registrazione"
            },
            "admin": {
                "main": "Amministratore",
                "userManagement": "Gestione utenti",
                "tracker": "Tracciamento utente",
                "metrics": "Metriche",
                "health": "Integrità",
                "configuration": "Configurazione",
                "logs": "Log",
                "audits": "Cambiamenti",
                "apidocs": "API",
                "database": "Database",
                "entity-audit": "Entity Audit",
                "jhipster-needle-menu-add-admin-element": "JH will add additional menu entries here (do not translate!)"
            },
            "language": "Lingua"
        },
        "search": {
            "buttonLabel": "Cerca",
            "filtersLabel": "Filtri Ricerca",
            "clearfiltersLabel": "Pulisci Filtri"
        },
        "form": {
            "username": "Utente",
            "username.placeholder": "Il tuo nome utente",
            "currentpassword": "Current password",
            "currentpassword.placeholder": "Current password",
            "newpassword": "Nuova password",
            "newpassword.placeholder": "Nuova password",
            "confirmpassword": "Conferma nuova password",
            "confirmpassword.placeholder": "Conferma nuova password",
            "email": "Email",
            "email.placeholder": "Il tuo indirizzo email"
        },
        "messages": {
            "info": {
                "authenticated": {
                    "prefix": "Se vuoi ",
                    "link": "accedere",
                    "suffix": ", puoi provare gli account di default:<#list authorities as authority><br/>- ${authority?cap_first} (login='${authority}' and password='${authority}')</#list>"
                },
                "register": {
                    "noaccount": "Non hai ancora un account?",
                    "link": "Crea un account"
                }
            },
            "error": {
                "dontmatch": "La password e la sua conferma non corrispondono!"
            },
            "validate": {
                "newpassword": {
                    "required": "La password è obbligatoria.",
                    "minlength": "La password deve essere di almeno 4 caratteri",
                    "maxlength": "La password non può contenere più di 50 caratteri",
                    "strength": "Robustezza della password:"
                },
                "confirmpassword": {
                    "required": "La password di conferma è obbligatoria.",
                    "minlength": "La tua password deve essere di almeno 4 caratteri",
                    "maxlength": "La tua password non può contenere più di 50 caratteri"
                },
                "email": {
                    "required": "L'indirizzo email è obbligatorio.",
                    "invalid": "L'indirizzo email non è valido.",
                    "minlength": "L'indirizzo email deve essere di almeno 5 caratteri",
                    "maxlength": "L'indirizzo email non può contenere più di 50 caratteri"
                }
            }
        },
        "field": {
            "id": "ID"
        },
        "ribbon": {
            "dev": "Development"
        },
        "item-count": "Mostra {{first}} - {{second}} di {{total}} articoli."
    },
    "entity": {
        "action": {
            "addblob": "Aggiungi blob",
            "addimage": "Aggiungi immagine",
            "back": "Indietro",
            "cancel": "Annulla",
            "delete": "Elimina",
            "edit": "Modifica",
            "open": "Open",
            "save": "Salva",
            "view": "Visualizza"
        },
        "detail": {
            "field": "Campo",
            "value": "Valore"
        },
        "delete": {
            "title": "Conferma operazione di cancellazione"
        },
        "validation": {
            "required": "Questo campo è obbligatorio.",
            "minlength": "Questo campo deve essere di almeno {{ min }} caratteri.",
            "maxlength": "Questo campo non può essere più di {{ max }} caratteri.",
            "min": "Questo campo dovrebbe essere più di {{ min }}.",
            "max": "Questo campo non può essere superiore a {{ max }}.",
            "minbytes": "Questo campo dovrebbe essere più di {{ min }} byte.",
            "maxbytes": "Questo campo non può essere superiore a {{ max }} byte.",
            "pattern": "Questo campo dovrebbe corrispondere al modello {{ pattern }}.",
            "number": "Questo campo dovrebbe essere un numero.",
            "datetimelocal": "Questo campo dovrebbe essere data e ora."
        }
    },
    "error": {
        "internalServerError": "Internal server error",
        "server.not.reachable": "Server non raggiungibile",
        "url.not.found": "Non trovato",
        "NotNull": "Il campo {{ fieldName }} non può essere vuoto!",
        "Size": "Il campo {{ fieldName }} non è conforme ai requisiti di lunghezza min/max!",
        "userexists": "Il nome utente è già in uso!",
        "emailexists": "L'email è già in uso!",
        "idexists": "Un(a) nuovo/a {{entityName}} non può già avere un ID",
        "idnull": "Invalid ID"
    },
    "footer": "Piè di pagina"
}
