package edu.kit.kastel.sdq.solidityroleadapter.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResultWriter {

	public void writeToResultTxtFile(final String uriOfTxt, List<String> linesOfResult) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(uriOfTxt),
				Charset.forName("UTF-8"));
		writer.write(String.join(System.lineSeparator(), linesOfResult));
		writer.close();
	}
	
	public void writeToResultJsonFile(final String uriOfJson, AffectedObjectSetDTO resultDTOForJson) throws IOException {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.writeValue(new File(uriOfJson), resultDTOForJson);
	}
}
