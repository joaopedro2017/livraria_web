package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConexaoRelatorio;

public class GraficoDAO {

    public List<Object[]> emprestimoPorMes(String empRes) throws SQLException {
        ArrayList<Object[]> resultado = new ArrayList<>();

        String sql = "SELECT (CASE (MONTH  (e.data" + empRes + "))\n"
                + "                WHEN 1 THEN 'Jan'\n"
                + "                WHEN 2 THEN 'Fev'\n"
                + "                WHEN 3 THEN 'Mar'\n"
                + "                WHEN 4 THEN 'Abr'\n"
                + "                WHEN 5 THEN 'Mai'\n"
                + "                WHEN 6 THEN 'Jun'\n"
                + "                WHEN 7 THEN 'Jul'\n"
                + "                WHEN 8 THEN 'Ago'\n"
                + "                WHEN 9 THEN 'Set'\n"
                + "                WHEN 10 THEN 'Out'\n"
                + "                WHEN 11 THEN 'Nov'\n"
                + "                WHEN 12 THEN 'Dez' END) as 'MÊS',\n"
                + "	COUNT(*) as 'Livros'\n"
                + "from  " + empRes + " e WHERE  MONTH (e.data" + empRes + ") BETWEEN (MONTH (CURRENT_DATE) - 3) AND (MONTH (CURRENT_DATE) - 1)\n"
                + "GROUP BY MONTH (e.data" + empRes + ")";

        try (Statement stmt = ConexaoRelatorio.getConexao().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object resul[] = new Object[2];
                resul[0] = rs.getString(1);
                resul[1] = rs.getLong(2);

                resultado.add(resul);
            }
        }
        ConexaoRelatorio.fecharConexao();
        return resultado;
    }

    public List<Object[]> emprestimoPorAssunto(String empRes) throws SQLException {
        ArrayList<Object[]> resultado = new ArrayList<>();

        String sql = "SELECT (CASE (MONTH  (r.data" + empRes + "))\n"
                + "                WHEN 1 THEN 'Jan'\n"
                + "                WHEN 2 THEN 'Fev'\n"
                + "                WHEN 3 THEN 'Mar'\n"
                + "                WHEN 4 THEN 'Abr'\n"
                + "                WHEN 5 THEN 'Mai'\n"
                + "                WHEN 6 THEN 'Jun'\n"
                + "                WHEN 7 THEN 'Jul'\n"
                + "                WHEN 8 THEN 'Ago'\n"
                + "                WHEN 9 THEN 'Set'\n"
                + "                WHEN 10 THEN 'Out'\n"
                + "                WHEN 11 THEN 'Nov'\n"
                + "                WHEN 12 THEN 'Dez' END) as 'MÊS',\n"
                + "		COUNT(*) as 'Livros',\n"
                + "		a.nomeAssunto as 'Assunto'\n"
                + "from " + empRes + " r\n"
                + "		INNER JOIN exemplar ex\n"
                + "        on r.Exemplar_id = ex.id\n"
                + "        INNER JOIN livro l\n"
                + "        on l.id = ex.Livro_id\n"
                + "        INNER JOIN assunto a\n"
                + "        on a.id = l.Assunto_id\n"
                + "WHERE MONTH (r.data" + empRes + ") BETWEEN (MONTH (CURRENT_DATE) - 3) AND (MONTH (CURRENT_DATE) - 1 ) \n"
                + "GROUP BY MONTH (r.data" + empRes + "), a.nomeAssunto ORDER BY a.nomeAssunto, MONTH (r.data" + empRes + ")";

        try (Statement stmt = ConexaoRelatorio.getConexao().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object resul[] = new Object[3];
                resul[0] = rs.getString(1);
                resul[1] = rs.getLong(2);
                resul[2] = rs.getString(3);

                resultado.add(resul);
            }
        }
        ConexaoRelatorio.fecharConexao();
        return resultado;
    }

    public Object[] reservaEmprestimoUltimoMes() throws SQLException {
        Object[] resultado = new Object[2];

        String sql = "SELECT (SELECT COUNT(*) FROM RESERVA r WHERE MONTH (r.dataReserva) = (MONTH (CURRENT_DATE) - 1))  as 'Reserva',\n"
                + "	(SELECT COUNT(*) FROM EMPRESTIMO em WHERE MONTH (em.dataEmprestimo) = (MONTH (CURRENT_DATE) - 1))  as 'Emprestimo' \n"
                + "from exemplar ex\n"
                + "        RIGHT JOIN reserva r\n"
                + "        ON r.Exemplar_id = ex.id\n"
                + "        RIGHT JOIN emprestimo em\n"
                + "        ON em.Exemplar_id = ex.id\n"
                + "GROUP by (MONTH (CURRENT_DATE) - 1)";

        try (Statement stmt = ConexaoRelatorio.getConexao().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                resultado[0] = rs.getLong(1);
                resultado[1] = rs.getLong(2);
            }
        }
        ConexaoRelatorio.fecharConexao();
        return resultado;

    }

}
