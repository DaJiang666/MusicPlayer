package com.turing.musicplayer.model;

import java.util.List;

/**
 *
 * Author: ZhangDanJiang
 *
 * Date:2016年6月15日 Time: 下午3:35:23
 *
 * Function: 返回事件 JavaBean
 *
 */
public class EventDataBean {
	
	private List<Behaviors> behaviors;

	public List<Behaviors> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(List<Behaviors> behaviors) {
		this.behaviors = behaviors;
	}

	class Behaviors {

		private List<ResultBean> results;

		private String exception;

		private List<ServiceBean> sequences;

		private IntentBean intent;

		

		public List<ResultBean> getResults() {
			return results;
		}

		public void setResults(List<ResultBean> results) {
			this.results = results;
		}

		public String getException() {
			return exception;
		}

		public void setException(String exception) {
			this.exception = exception;
		}

		public List<ServiceBean> getSequences() {
			return sequences;
		}

		public void setSequences(List<ServiceBean> sequences) {
			this.sequences = sequences;
		}

		public IntentBean getIntent() {
			return intent;
		}

		public void setIntent(IntentBean intent) {
			this.intent = intent;
		}

		class ResultBean {
			private ValuesBean values;
			private String resultType;

			public ValuesBean getValues() {
				return values;
			}

			public void setValues(ValuesBean values) {
				this.values = values;
			}

			public String getResultType() {
				return resultType;
			}

			public void setResultType(String resultType) {
				this.resultType = resultType;
			}

			
			class ValuesBean {
				String text;

				public String getText() {
					return text;
				}

				public void setText(String text) {
					this.text = text;
				}
				
			}
		}

		class ServiceBean {
			private String service;

			public String getService() {
				return service;
			}

			public void setService(String service) {
				this.service = service;
			}
		}

		class IntentBean {
			
			private int appId;
			private int parseType;
			private ParametersBean parameters;
			private int code;
			private String type;

			public int getAppId() {
				return appId;
			}

			public void setAppId(int appId) {
				this.appId = appId;
			}

			public int getParseType() {
				return parseType;
			}

			public void setParseType(int parseType) {
				this.parseType = parseType;
			}

			public ParametersBean getParameters() {
				return parameters;
			}

			public void setParameters(ParametersBean parameters) {
				this.parameters = parameters;
			}

			public int getCode() {
				return code;
			}

			public void setCode(int code) {
				this.code = code;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}


			class ParametersBean {
				private String song;
				private String singer;
				private String text_spare;
				private int parse_type;
				
				public String getSong() {
					return song;
				}
				public void setSong(String song) {
					this.song = song;
				}
				public String getSinger() {
					return singer;
				}
				public void setSinger(String singer) {
					this.singer = singer;
				}
				public String getText_spare() {
					return text_spare;
				}
				public void setText_spare(String text_spare) {
					this.text_spare = text_spare;
				}
				public int getParse_type() {
					return parse_type;
				}
				public void setParse_type(int parse_type) {
					this.parse_type = parse_type;
				}
				
			}
		}

	}

}
