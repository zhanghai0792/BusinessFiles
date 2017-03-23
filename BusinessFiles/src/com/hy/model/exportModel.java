package com.hy.model;

import javax.persistence.Transient;

public interface exportModel {
 public Integer getId();
 @Transient
 public String getFoldName();
}
