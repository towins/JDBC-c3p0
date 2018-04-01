package org.forten.jdbcdemo.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.forten.jdbcdemo.model.Student;
import org.forten.jdbcdemo.util.JDBCUtil;

public class StudentBo {
	public static int doSave(Student s) {
		// 编写“准备SQL语句”
		// 使用?作为参数值的占位符，具体的值需要使用PreparedStatement对象的参数绑定方法进行绑定
		String sql = "INSERT INTO students (name,gender,email,birthday,score,regist_time) VALUES (?,?,?,?,?,?)";
		System.out.println(sql);

		Connection conn = JDBCUtil.getConnectionForMySQL(false);

		PreparedStatement stat = null;
		int n = 0;
		try {
			// 得到一个准备语句对象，它是一般语句对象的子接口
			// PreparedStatement是Statement的子接口
			// 在得到准备语句对象时，需要把要执行的SQL语句传入到它的构造方法中
			// 因为要在后续的代码中进行参数值与占位符的绑定，要让准备语句对象事先了解SQL语句的逻辑
			stat = conn.prepareStatement(sql);
			// 向准备语句对象中绑定参数值
			// 是使用PreparedStatement的setXXX(paramIndex,value)来绑定参数值的
			// 第一个?与Student对象的name值进行了绑定，因为name是String类型的，所以使用setString方法
			stat.setString(1, s.getName());
			stat.setString(2, s.getGender());
			stat.setString(3, s.getEmail());
			stat.setDate(4, s.getBirthdayForDB());
			stat.setTimestamp(6, s.getRegistTimeForDB());
			stat.setInt(5, s.getScore());

			// 以下语句会再现com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an
			// error in your SQL syntax; check the manual that corresponds to your MySQL
			// server version for the right syntax to use near '?,?,?,?,?,?)' at line 1
			// 因为sql已经在conn.prepareStatement(sql)时被绑定在PreparedStatement对象上了
			// 而参数值也通过上边几行代码绑定好了，所以我们应该直接调用stat.executeUpdate();
			// n = stat.executeUpdate(sql);
			// 执行准备语句
			n = stat.executeUpdate();
			// 因为获取数据库连接时，把自动提交设置为了false
			// 所以要进行数据的磁盘持久化操作一定要手动提交
			conn.commit();
			System.out.println("添加了" + n + "条学生信息");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, stat);
		}

		return n;
	}

	public static int doDelete(int id) {
		String sql = "DELETE FROM students WHERE id=?";
		Connection conn = JDBCUtil.getConnectionForMySQL();
		PreparedStatement stat = null;
		int n = -1;
		try {
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);

			n = stat.executeUpdate();
			System.out.println("删除成功");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, stat);
		}
		return n;
	}

	public static int doUpdate(Student s) {
		String sql = "UPDATE students SET name=?,email=?,gender=?,birthday=?,score=? WHERE id=?";
		Connection conn = JDBCUtil.getConnectionForMySQL();
		PreparedStatement stat = null;
		int n = -1;
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, s.getName());
			stat.setString(2, s.getEmail());
			stat.setString(3, s.getGender());
			stat.setDate(4, s.getBirthdayForDB());
			stat.setInt(5, s.getScore());
			stat.setInt(6, s.getId());

			n = stat.executeUpdate();
			System.out.println("更新成功");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, stat);
		}
		return n;
	}

	public static List<Student> queryByName(String name) {
		// SQL语句
		String sql = "SELECT * FROM students WHERE name LIKE ?";
		// 返回的装有若干学生对象的List集合，目前是空的
		List<Student> list = new ArrayList<>();

		// 连接
		Connection conn = JDBCUtil.getConnectionForMySQL();
		// 声明准备语句和结果集合
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			// 把SQL传入到准备语句对象中
			stat = conn.prepareStatement(sql);
			// 绑定参数，如果是模糊查询，则应该在值前后加%
			stat.setString(1, "%" + name + "%");

			// 执行查询，得到rs
			rs = stat.executeQuery();
			// 遍历数据结果集合，把rs中的数据转换为Student类型的对象，并放入list中
			while (rs.next()) {
				// 得每一条数据中的每个单元格中的数据
				int id = rs.getInt("id");
				int score = rs.getInt("score");
				String sname = rs.getString("name");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				Date birthday = rs.getDate("birthday");
				Date registTime = rs.getTimestamp("regist_time");

				// 使用以上得到的数据实例化一个Student对象，并填入数据
				Student s = new Student(sname, gender, birthday, email, score);
				s.setId(id);
				s.setRegistTime(registTime);

				// 向返回的list中逐个追加封装好的Student对象
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, stat, rs);
		}

		return list;
	}
}
