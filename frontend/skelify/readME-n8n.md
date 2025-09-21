# n8n workflow

Questo workflow prende un ZIP di progetto (Spring/Angular), lo spacchetta, manda ogni file a un LLM per trasformarlo in template FreeMarker (.ftl) e alla fine restituisce uno ZIP con i template.


# Note di configurazione

Crea una credential HTTP Header Auth in n8n con:
Name: Authorization
Value: Bearer <la tua OPENAI_API_KEY>

Input: POST su http://<n8n-host>/webhook/transform-to-template
Body JSON esempio:

{
  "projectZip": "/tmp/myproject.zip"
}


Output: zip templates.zip con la struttura trasformata in .ftl.


### bash Command
docker run -it --rm \
  -p 5678:5678 \
  -v ~/.n8n:/home/node/.n8n \
  n8nio/n8n

  
  Now open http://localhost:5678



## N8N Personal Finance Dashboard
Ecco il file JSON di workflow per n8n che puoi importare direttamente.

- Legge i dati da Google Sheets (ticker, prezzoMedio, quantita)
- Recupera i prezzi da Yahoo Finance
- Calcola P/L e strategia
- Crea una Dashboard HTML
- Invia il report via Gmail

### 📂 File workflow_portafoglio.json

{
  "name": "Portafoglio Azioni - Yahoo + Gmail",
  "nodes": [
    {
      "parameters": {},
      "id": "1",
      "name": "Trigger Giornaliero",
      "type": "n8n-nodes-base.cron",
      "typeVersion": 1,
      "position": [250, 300]
    },
    {
      "parameters": {
        "operation": "read",
        "sheetId": "INSERISCI_ID_DEL_TUO_GOOGLE_SHEET",
        "range": "Portafoglio!A:C"
      },
      "id": "2",
      "name": "Google Sheets",
      "type": "n8n-nodes-base.googleSheets",
      "typeVersion": 3,
      "position": [500, 300],
      "credentials": {
        "googleApi": "Google API Account"
      }
    },
    {
      "parameters": {
        "url": "https://query1.finance.yahoo.com/v7/finance/quote?symbols={{$json[\"ticker\"]}}",
        "responseFormat": "json"
      },
      "id": "3",
      "name": "Yahoo Finance",
      "type": "n8n-nodes-base.httpRequest",
      "typeVersion": 2,
      "position": [750, 300]
    },
    {
      "parameters": {
        "functionCode": "const results = [];\n\nfor (const item of items) {\n  const ticker = item.json.ticker;\n  const prezzoMedio = parseFloat(item.json.prezzoMedio);\n  const quantita = parseFloat(item.json.quantita);\n  const prezzoAttuale = item.json.quoteResponse.result[0].regularMarketPrice;\n\n  const totaleInvestito = prezzoMedio * quantita;\n  const valoreAttuale = prezzoAttuale * quantita;\n  const profitLoss = valoreAttuale - totaleInvestito;\n  const inPerdita = profitLoss < 0;\n\n  let strategia = \"Mantieni posizione\";\n  if (inPerdita) {\n    strategia = \"Valuta mediazione al ribasso o stop loss\";\n  } else {\n    strategia = \"Puoi mantenere o alleggerire la posizione\";\n  }\n\n  results.push({\n    json: {\n      ticker,\n      prezzoMedio,\n      prezzoAttuale,\n      quantita,\n      totaleInvestito,\n      valoreAttuale,\n      profitLoss,\n      strategia\n    }\n  });\n}\n\nreturn results;"
      },
      "id": "4",
      "name": "Calcoli",
      "type": "n8n-nodes-base.function",
      "typeVersion": 1,
      "position": [1000, 300]
    },
    {
      "parameters": {
        "functionCode": "let totaleInvestito = 0;\nlet totaleAttuale = 0;\n\nitems.forEach(i => {\n  totaleInvestito += i.json.totaleInvestito;\n  totaleAttuale += i.json.valoreAttuale;\n});\n\nconst totalePL = totaleAttuale - totaleInvestito;\n\nlet dashboard = `\n<h2>📊 Dashboard Portafoglio</h2>\n<table border=\"1\" cellspacing=\"0\" cellpadding=\"5\">\n<tr><th>Ticker</th><th>Investito</th><th>Attuale</th><th>P/L</th><th>Strategia</th></tr>\n`;\n\nitems.forEach(i => {\n  dashboard += `<tr>\n    <td>${i.json.ticker}</td>\n    <td>${i.json.totaleInvestito.toFixed(2)} €</td>\n    <td>${i.json.valoreAttuale.toFixed(2)} €</td>\n    <td style=\\\"color:${i.json.profitLoss<0 ? 'red':'green'}\\\">${i.json.profitLoss.toFixed(2)} €</td>\n    <td>${i.json.strategia}</td>\n  </tr>`;\n});\n\ndashboard += `</table>\n<p><b>Totale Investito:</b> ${totaleInvestito.toFixed(2)} €</p>\n<p><b>Valore Attuale:</b> ${totaleAttuale.toFixed(2)} €</p>\n<p><b>P/L Totale:</b> ${totalePL.toFixed(2)} €</p>\n`;\n\nreturn [{ json: { dashboard } }];"
      },
      "id": "5",
      "name": "Crea Dashboard",
      "type": "n8n-nodes-base.function",
      "typeVersion": 1,
      "position": [1250, 300]
    },
    {
      "parameters": {
        "fromEmail": "TUO_ACCOUNT_GMAIL@gmail.com",
        "toEmail": "DESTINATARIO@gmail.com",
        "subject": "📈 Report Giornaliero Portafoglio",
        "htmlBody": "{{$json[\"dashboard\"]}}"
      },
      "id": "6",
      "name": "Invia Gmail",
      "type": "n8n-nodes-base.gmail",
      "typeVersion": 1,
      "position": [1500, 300],
      "credentials": {
        "gmailOAuth2": "Gmail Account"
      }
    }
  ],
  "connections": {
    "Trigger Giornaliero": {
      "main": [
        [
          {
            "node": "Google Sheets",
            "type": "main",
            "index": 0
          }
        ]
      ]
    },
    "Google Sheets": {
      "main": [
        [
          {
            "node": "Yahoo Finance",
            "type": "main",
            "index": 0
          }
        ]
      ]
    },
    "Yahoo Finance": {
      "main": [
        [
          {
            "node": "Calcoli",
            "type": "main",
            "index": 0
          }
        ]
      ]
    },
    "Calcoli": {
      "main": [
        [
          {
            "node": "Crea Dashboard",
            "type": "main",
            "index": 0
          }
        ]
      ]
    },
    "Crea Dashboard": {
      "main": [
        [
          {
            "node": "Invia Gmail",
            "type": "main",
            "index": 0
          }
        ]
      ]
    }
  }
}
