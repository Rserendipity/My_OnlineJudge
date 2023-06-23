package dao;

import common.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDAO {
    public void insert(Problem problem) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MyDB.getConnection();
            String sql = "insert into oj_table values (null, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, problem.getTitle());
            statement.setString(2, problem.getLevel());
            statement.setString(3, problem.getDescription());
            statement.setString(4, problem.getCodeTemplate());
            statement.setString(5, problem.getCodeTest());
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("题目新增失败");
            } else {
                System.out.println("新增成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MyDB.close(connection, statement, null);
        }
    }

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MyDB.getConnection();
            String sql = "delete from oj_table where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("题目删除失败");
            } else {
                System.out.println("删除成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MyDB.close(connection, statement, null);
        }
    }

    public List<Problem> selectAll() {
        List<Problem> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MyDB.getConnection();
            String sql = "select id, title, level from oj_table";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Problem problem = new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                list.add(problem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MyDB.close(connection, statement, resultSet);
        }
        return list;
    }

    public Problem selectOne(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MyDB.getConnection();
            String sql = "select * from oj_table where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Problem problem = new Problem();
            if (resultSet.next()) {
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                problem.setDescription(resultSet.getString("description"));
                problem.setCodeTemplate(resultSet.getString("codeTemplate"));
                problem.setCodeTest(resultSet.getString("codeTest"));
                return problem;
            } else {
                System.out.println("select error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MyDB.close(connection, statement, resultSet);
        }
        return null;
    }


//    static void testInsert() {
//        ProblemDAO problemDAO = new ProblemDAO();
//        Problem problem = new Problem();
//        problem.setTitle("两数之和");
//        problem.setLevel("简单");
//        problem.setDescription("给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。\n" +
//                "\n" +
//                "你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n" +
//                "\n" +
//                "你可以按任意顺序返回答案。\n" +
//                "\n" +
//                " \n" +
//                "\n" +
//                "示例 1：\n" +
//                "\n" +
//                "输入：nums = [2,7,11,15], target = 9\n" +
//                "输出：[0,1]\n" +
//                "解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。\n" +
//                "示例 2：\n" +
//                "\n" +
//                "输入：nums = [3,2,4], target = 6\n" +
//                "输出：[1,2]\n" +
//                "示例 3：\n" +
//                "\n" +
//                "输入：nums = [3,3], target = 6\n" +
//                "输出：[0,1]\n" +
//                " \n" +
//                "\n" +
//                "提示：\n" +
//                "\n" +
//                "2 <= nums.length <= 104\n" +
//                "-109 <= nums[i] <= 109\n" +
//                "-109 <= target <= 109\n" +
//                "只会存在一个有效答案\n" +
//                "\n" +
//                "来源：力扣（LeetCode）\n" +
//                "链接：https://leetcode.cn/problems/two-sum\n" +
//                "著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。");
//        problem.setCodeTemplate("class Solution {\n" +
//                "    public int[] twoSum(int[] nums, int target) {\n" +
//                "\n" +
//                "    }\n" +
//                "}");
//        problem.setCodeTest("public static void main(String[] args) {\n" +
//                "        Solution solution = new Solution();\n" +
//                "        // testcase1\n" +
//                "        int[] nums = {2,7,11,15};\n" +
//                "        int target = 9;\n" +
//                "        int[] result = solution.twoSum(nums, target);\n" +
//                "        if (result.length == 2 && result[0] == 0 && result[1] == 1) {\n" +
//                "            System.out.println(\"testcase1 OK\");\n" +
//                "        } else {\n" +
//                "            System.out.println(\"testcase1 failed!\");\n" +
//                "        }\n" +
//                "\n" +
//                "        // testcase2\n" +
//                "        int[] nums2 = {3,2,4};\n" +
//                "        int target2 = 6;\n" +
//                "        int[] result2 = solution.twoSum(nums2, target2);\n" +
//                "        if (result2.length == 2 && result[0] == 1 && result[1] == 2) {\n" +
//                "            System.out.println(\"testcase2 OK\");\n" +
//                "        } else {\n" +
//                "            System.out.println(\"testcase2 failed!\");\n" +
//                "        }\n" +
//                "    }");
//        problemDAO.insert(problem);
//    }
//
//    static void testSelectAll() {
//        ProblemDAO problemDAO = new ProblemDAO();
//        List<Problem> problems = problemDAO.selectAll();
//        for (Problem problem : problems) {
//            System.out.println(problem);
//        }
//    }
//
//    static void testSelectOne(int id) {
//        ProblemDAO problemDAO = new ProblemDAO();
//        Problem problem = problemDAO.selectOne(id);
//        System.out.println(problem);
//    }
//
//    static void testDelete(int id) {
//        ProblemDAO problemDAO = new ProblemDAO();
//        problemDAO.delete(id);
//    }
//
//    public static void main(String[] args) {
//        testSelectAll();
//        testInsert();
//        testSelectOne(2);
//        testDelete(2);
//        System.out.println("------------");
//        testSelectAll();
//    }
}
