package com.hy.dao;

import java.io.InputStream;
import java.util.List;

import com.hy.model.Certificate;

public interface CertificateDao extends baseDao2 {
	public List<Certificate> getList(Certificate cm,String hql);
	public boolean add(Certificate le);

	public boolean update(Certificate le);

	public int delete(List<Integer> ids);
	public InputStream exportData(String[]  idss);
	public Certificate getIdObject(String id);
	public Certificate getCertificateById(Integer id);
	public void evict(Certificate c);
}
