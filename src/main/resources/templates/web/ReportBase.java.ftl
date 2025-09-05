package ${packageName}.${webRestPackage};

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public abstract class ReportBase {
    private final Logger log = LoggerFactory.getLogger(ReportBase.class);
	@Autowired
	private DataSource dataSource;

	public Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<InputStreamResource> responseFromStream(InputStream stream, String fileName){
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
				.contentType(MediaType.parseMediaType("application/pdf"))
				.body(new InputStreamResource(stream));
	}

	public ResponseEntity<InputStreamResource> responseFromFile(File file, String fileName){
		try {
			return responseFromStream(new FileInputStream(file), fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JasperPrint getJasperPrint(String path, HashMap<String, Object> params) throws JRException, FileNotFoundException {
		log.debug("getJasperPrint from path: " + path);
		InputStream inputStream = new FileInputStream(new File(path));
		return getJasperPrint(inputStream, params);
	}

	public JasperPrint getJasperPrint(InputStream inputStream, HashMap<String, Object> params) throws JRException {
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(params!=null) {
			parameters.putAll(params);
		}
		return JasperFillManager.fillReport(jasperReport, parameters, getConnection());
	}

}
