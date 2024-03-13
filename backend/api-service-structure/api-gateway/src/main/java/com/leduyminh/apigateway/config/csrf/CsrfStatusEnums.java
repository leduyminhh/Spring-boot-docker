package ;

public enum CsrfStatusEnums {
	valid(1, "CSRF_TOKEN hợp lệ"), Invalid(2, "CSRF_TOKEN không hợp lệ"),
	Not_found(3, "Không tìm thấy giá trị CSRF_TOKEN và CSRF_SESSION"),Ignore(4, "Không kiểm tra CSRF_TOKEN");

	private int status;
	private String messeage;

	private CsrfStatusEnums(int status, String messeage) {
		this.status = status;
		this.messeage = messeage;
	}

	public int getStatus() {
		return status;
	}

	public String getMesseage() {
		return messeage;
	}

}
