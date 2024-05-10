package com.truper.poc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@SpringBootTest
class PocApplicationTests {
	Logger LOG = LoggerFactory.getLogger(PocApplicationTests.class);

	@Test
	void contextLoads()  throws Exception{

		

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			ReponseDataExtend reponseDataExtend = new ReponseDataExtend();
			reponseDataExtend.setApelldo("camchio");
			reponseDataExtend.setNombre("crichie");
			reponseDataExtend.setAuto("swift");
			reponseDataExtend.setOcupacion("dev");
			reponseDataExtend.setCompu("dell");

			LOG.info(reponseDataExtend.toString());

			String extendedAsStirng=   mapper.writeValueAsString(reponseDataExtend);
			

			LOG.info( " Extendeds as String {} ",extendedAsStirng );

			ReponseDataReduced dataReduced =  mapper.readValue(extendedAsStirng,ReponseDataReduced.class );

			LOG.info( "Reduced String {} ",dataReduced );

			assertTrue(reponseDataExtend.getNombre().equals( dataReduced.getNombre())  );
			
		

	}



}
@Data
class ReponseDataReduced {
	private String  nombre;
	private String apelldo;
	private String ocupacion;

	public ReponseDataReduced  (){}
	
}

@Data
class ReponseDataExtend {
	private String  nombre;
	private String apelldo;
	private String ocupacion;
	
	private String auto;
	private String compu;

	public  ReponseDataExtend () {}
}
