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