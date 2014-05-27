/**
 *  Copyright 2014 Diego Ceccarelli
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package it.cnr.isti.hpc.erd;

import java.util.List;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on May 27, 2014
 */
public class ErdAnnotation {
	private String docId;
	private int start;
	private int end;

	private String primaryId = "";
	private String secondId = "";

	private String mentionText;
	private double score1;
	private double score2;

	public static String encodeAnnotations(List<ErdAnnotation> annotations) {
		StringBuilder sb = new StringBuilder();
		for (ErdAnnotation a : annotations) {
			sb.append(a.toTsv()).append('\n');
		}
		// delete the last newline;
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public String toTsv() {
		StringBuilder sb = new StringBuilder();
		sb.append(docId).append('\t');
		sb.append(start).append('\t');
		sb.append(end).append('\t');
		sb.append(primaryId).append('\t');
		sb.append(secondId).append('\t');
		sb.append(mentionText).append('\t');
		sb.append(score1).append('\t');
		sb.append(score2);
		return sb.toString();

	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getSecondId() {
		return secondId;
	}

	public void setSecondId(String secondId) {
		this.secondId = secondId;
	}

	public String getMentionText() {
		return mentionText;
	}

	public void setMentionText(String mentionText) {
		this.mentionText = mentionText;
	}

	public double getScore1() {
		return score1;
	}

	public void setScore1(double score1) {
		this.score1 = score1;
	}

	public double getScore2() {
		return score2;
	}

	public void setScore2(double score2) {
		this.score2 = score2;
	}

}