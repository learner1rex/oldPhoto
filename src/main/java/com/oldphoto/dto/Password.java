package com.oldphoto.dto;

import lombok.Data;

@Data
public class Password {
  private String oldPwd;
  private String newPwd;
  private String rePwd;
}
