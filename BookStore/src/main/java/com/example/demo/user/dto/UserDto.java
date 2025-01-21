package com.example.demo.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
	
	
	@NotEmpty(message = "名前を入力してください")
	@Size(max = 50, message = "名前は50文字以内で入力してください")
	private String userRealName;
	
	@NotEmpty(message = "パスワードを入力してください")
	@Size(max = 100, message = "パスワードは100桁以内で入力してください")
	private String userPass;
	
	@NotEmpty(message = "メールアドレスを入力してください")
	@Size(max = 50, message = "メールアドレスは50文字以内で入力してください")
	private String userMail;
	
	@NotEmpty(message = "住所を入力してください")
	@Size(max = 255, message = "住所は255文字以内で入力してください")
	private String userAddress;
	
	@NotEmpty(message = "誕生日を入力してください")
	private String userBirthday;
	
	@NotEmpty(message = "電話番号を入力してください")
	@Pattern(regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}", message = "電話番号の形式で入力してください")
	private String userTel;
	
	@Size(max = 16, message = "クレジット番号は16桁以内で入力してください")
	private String userCreditnum;
	
	
	
}
