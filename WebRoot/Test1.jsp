<%@ page language="java" pageEncoding="GBK" %>

<html>
<head>
    <title>Testing</title>
</head>

<body>
<form action="actionTest.testing.do" method="post">
    <table>
        <tr>
            <td>
                Name ��
            </td>
            <td>
                <input type="text" name="name" value="${name}"/>
            </td>
        </tr>
        <tr>
            <td>
                PassWord ��
            </td>
            <td>
                <input type="text" name="password" value="${password}"/>
            </td>
        </tr>
        <tr>
            <td>
                Age ��
            </td>
            <td>
                <input type="text" name="age" value="${age}"/>
            </td>
        </tr>
    </table>

    <input type="submit" id="subBtn" value="�ύ"/>

</form>
</body>
</html>