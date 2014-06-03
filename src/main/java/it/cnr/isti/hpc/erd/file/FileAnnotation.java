///**
// *  Copyright 2014 Diego Ceccarelli
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// * 
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//package it.cnr.isti.hpc.erd.file;
//
//import it.cnr.isti.hpc.io.IOUtils;
//import it.cnr.isti.hpc.io.reader.RecordParser;
//import it.cnr.isti.hpc.io.reader.RecordReader;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import mpi.aida.util.freebase.FreebaseYagoMapperERD;
//
//import com.google.gson.Gson;
//
///**
// * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
// * 
// *         Created on May 30, 2014
// */
//public class FileAnnotation {
//	String docId;
//	String mention;
//	int start;
//	int end;
//	String entity;
//	String freebaseId;
//	float confidence;
//
//	static FreebaseYagoMapperERD yago = FreebaseYagoMapperERD.singleton();
//
//	private static Pattern p = Pattern
//			.compile("\\] (.*) \\( (.*)/(.*)\\) -> (.*)");
//	private static Pattern e = Pattern.compile("(.*)\\((.*)\\)[,].*");
//
//	@Override
//	public String toString() {
//		return "FileAnnotation [docId=" + docId + ", mention=" + mention
//				+ ", start=" + start + ", end=" + end + ", entity=" + entity
//				+ ", freebaseId=" + freebaseId + ", confidence=" + confidence
//				+ "]";
//	}
//
//	public static List<FileAnnotation> load(String file) {
//		RecordReader<FileAnnotation> reader = new RecordReader<FileAnnotation>(
//				file, new Parser());
//		List<FileAnnotation> annotations = new LinkedList<FileAnnotation>();
//		for (FileAnnotation ann : reader) {
//			if (ann == null)
//				continue;
//
//			annotations.add(ann);
//		}
//		return annotations;
//
//	}
//
//	public String getDocId() {
//		return docId;
//	}
//
//	public void setDocId(String docId) {
//		this.docId = docId;
//	}
//
//	public String getMention() {
//		return mention;
//	}
//
//	public void setMention(String mention) {
//		this.mention = mention;
//	}
//
//	public int getStart() {
//		return start;
//	}
//
//	public void setStart(int start) {
//		this.start = start;
//	}
//
//	public int getEnd() {
//		return end;
//	}
//
//	public void setEnd(int end) {
//		this.end = end;
//	}
//
//	public String getEntity() {
//		return entity;
//	}
//
//	public void setEntity(String entity) {
//		this.entity = entity;
//	}
//
//	public String getFreebaseId() {
//		return freebaseId;
//	}
//
//	public void setFreebaseId(String freebaseId) {
//		this.freebaseId = freebaseId;
//	}
//
//	public float getConfidence() {
//		return confidence;
//	}
//
//	public void setConfidence(float confidence) {
//		this.confidence = confidence;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + Float.floatToIntBits(confidence);
//		result = prime * result + ((docId == null) ? 0 : docId.hashCode());
//		result = prime * result + end;
//		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
//		result = prime * result
//				+ ((freebaseId == null) ? 0 : freebaseId.hashCode());
//		result = prime * result + ((mention == null) ? 0 : mention.hashCode());
//		result = prime * result + start;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		FileAnnotation other = (FileAnnotation) obj;
//		if (Float.floatToIntBits(confidence) != Float
//				.floatToIntBits(other.confidence))
//			return false;
//		if (docId == null) {
//			if (other.docId != null)
//				return false;
//		} else if (!docId.equals(other.docId))
//			return false;
//		if (end != other.end)
//			return false;
//		if (entity == null) {
//			if (other.entity != null)
//				return false;
//		} else if (!entity.equals(other.entity))
//			return false;
//		if (freebaseId == null) {
//			if (other.freebaseId != null)
//				return false;
//		} else if (!freebaseId.equals(other.freebaseId))
//			return false;
//		if (mention == null) {
//			if (other.mention != null)
//				return false;
//		} else if (!mention.equals(other.mention))
//			return false;
//		if (start != other.start)
//			return false;
//		return true;
//	}
//
//	public static class Parser implements RecordParser<FileAnnotation> {
//
//		public FileAnnotation decode(String record) {
//			// System.out.println("\n\nrecord : -> " + record);
//			FileAnnotation ann = new FileAnnotation();
//			String[] items = record.split("\t");
//			ann.docId = items[0];
//			Matcher m = p.matcher(items[1]);
//			if (!m.matches()) {
//				// System.out.println("result: -> skip\n\n");
//				return null;
//			}
//			ann.mention = m.group(1);
//			ann.start = Integer.parseInt(m.group(3));
//			int length = Integer.parseInt(m.group(2));
//			ann.end = ann.start + length - 1;
//			String str = m.group(4);
//			if (str.contains("[]")) {
//				// System.out.println("result: -> skip\n\n");
//				return null;
//			}
//			String ett = str.substring(1);
//			String t = ett.split("\\)[,\\]]")[0];
//			int pos = t.lastIndexOf("(");
//			ann.entity = t.substring(0, pos);
//			ann.entity = ann.entity.replaceAll("YAGO:", "");
//			ann.confidence = Float.parseFloat(t.substring(pos + 1));
//			ann.freebaseId = yago.getFreebaseId(ann.entity);
//
//			// System.out.println("result: -> " + ann + "\n\n");
//
//			return ann;
//
//		}
//
//		public String encode(FileAnnotation arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//	}
//
//	private static Gson gson = new Gson();
//
//	public static void main(String[] args) throws IOException {
//		Parser p = new Parser();
//		p.decode("mainbody-00003	] Internet ( 8/260) -> [YAGO:Internet_service_provider(0.35714), YAGO:World_Wide_Web(0.21429), YAGO:Internet_television(0.14286), YAGO:Internet_access(0.14286), YAGO:Internet_Archive(0.07143), YAGO:Internet_Explorer(0.07143), YAGO:Demon_Internet(0), YAGO:Internet_Service_Providers_Association(0), YAGO:Pacific_Internet(0), YAGO:Positive_Internet(0)]");
//		BufferedWriter bw = IOUtils
//				.getPlainOrCompressedUTF8Writer("/tmp/aida-local.json");
//		List<FileAnnotation> ann = FileAnnotation.load("/tmp/all1");
//		for (FileAnnotation f : ann) {
//			bw.write(gson.toJson(f));
//			bw.newLine();
//		}
//		bw.close();
//	}
//
// }
