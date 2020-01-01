package com.nsta.w2.HibernateUtils;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.nsta.w2.Models.Word;

public class TestConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
	 
	      try {
	          // Tất cả các lệnh hành động với DB thông qua Hibernate
	          // đều phải nằm trong 1 giao dịch (Transaction)
	          // Bắt đầu giao dịch
	          session.getTransaction().begin();
	 
	          // Tạo một câu lệnh HQL query object.
	          // Tương đương với Native SQL:
	          // Select e.* from EMPLOYEE e order by e.EMP_NAME, e.EMP_NO
	 
	          String sql = "Select w from " + Word.class.getName() + " w "
	                  + " order by w.id";
	 
	          // Tạo đối tượng Query.
	          Query<Word> query = session.createQuery(sql);
	 
	          // Thực hiện truy vấn.
	          List<Word> listWord = query.getResultList();
	 
	          for (Word w : listWord) {
	              System.out.println(w);
	          }
	 
	          // Commit dữ liệu
	          session.getTransaction().commit();
	      } catch (Exception e) {
	          e.printStackTrace();
	          // Rollback trong trường hợp có lỗi xẩy ra.
	          session.getTransaction().rollback();
	      }
	}

}
