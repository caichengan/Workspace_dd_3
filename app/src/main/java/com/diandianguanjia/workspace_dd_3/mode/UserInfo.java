package com.diandianguanjia.workspace_dd_3.mode;

/**
 * 用户信息实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-9 下午5:02:52
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class UserInfo {


	/**
	 * code : 1
	 * message : {"captain_id":"122","jkey":"39KnqIToHYQrhac1izGSebP4DOgF7E8C","username":"粤T-401","head_img":"http://weixin.dd1760.com/"}
	 */

	private int code;
	private MessageBean message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}

	public static class MessageBean {
		/**
		 * captain_id : 122
		 * jkey : 39KnqIToHYQrhac1izGSebP4DOgF7E8C
		 * username : 粤T-401
		 * head_img : http://weixin.dd1760.com/
		 */

		private String captain_id;
		private String jkey;
		private String username;
		private String head_img;

		public String getCaptain_id() {
			return captain_id;
		}

		public void setCaptain_id(String captain_id) {
			this.captain_id = captain_id;
		}

		public String getJkey() {
			return jkey;
		}

		public void setJkey(String jkey) {
			this.jkey = jkey;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getHead_img() {
			return head_img;
		}

		public void setHead_img(String head_img) {
			this.head_img = head_img;
		}
	}
}
