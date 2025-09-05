package ${packageName}.${webRestPackage};

import java.io.FileNotFoundException;
import java.util.HashMap;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;

@RestController
@RequestMapping("/api")
public class ${className} extends ReportBase {
    private final Logger log = LoggerFactory.getLogger(${className}.class);
    private final String invoice_template_path = "/report/${entityFieldName}.jrxml";

    @GetMapping("/report/${entityFieldName}/{id}")
    @Timed
    public ResponseEntity<InputStreamResource> ${entityFieldName}Report(@PathVariable("id") Long id) throws FileNotFoundException, JRException {
        log.debug("GET /report/${entityFieldName}:id");
        HashMap<String, Object> params = new HashMap<>();
		params.put("id", id);
		jasperPrint = getJasperPrint(invoice_template_path, params);
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
		return responseFromStream(pdf, "${entityFieldName}.pdf");
	}

}
