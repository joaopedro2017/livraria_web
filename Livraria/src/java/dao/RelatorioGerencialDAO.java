package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConexaoRelatorio;

public class RelatorioGerencialDAO {

    public List<Object[]> relatorioGerencial(String dataInicial, String dataFinal) throws SQLException {
        ArrayList<Object[]> resultado = new ArrayList<>();

        String sql = "SELECT l.id,\n"
                + "	l.titulo as Titulo,\n"
                + "	l.isbn,\n"
                + "	l.edicao,\n"
                + "	l.ano,\n"
                + "	a.nomeAssunto,\n"
                + "	ed.nomeEditora,\n"
                + "	IFNULL(emp.qtd, 0) as Emprestimo,\n"
                + "	IFNULL(res.qtd, 0) as Reserva        \n"
                + "        	FROM Livro l \n"
                + "      	LEFT JOIN (SELECT ex.Livro_id,\n"
                + "                 	COUNT(*) qtd \n"
                + "                   		FROM Emprestimo e ,Exemplar ex \n"
                + "                	WHERE e.Exemplar_id = ex.id \n"
                + "                   AND e.dataEmprestimo BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'\n"
                + "                   GROUP BY ex.Livro_id) emp \n"
                + "      	ON l.id= emp.Livro_id \n"
                + "        LEFT JOIN (SELECT ex.Livro_id ,\n"
                + "                   COUNT(*) qtd \n"
                + "                   		FROM Reserva r ,Exemplar ex \n"
                + "                   WHERE r.Exemplar_id = ex.id \n"
                + "                   AND r.dataReserva BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'\n"
                + "                   GROUP BY ex.Livro_id) res \n"
                + "    	ON l.id = res.Livro_id\n"
                + "	INNER JOIN assunto a\n"
                + "        ON l.Assunto_id = a.id\n"
                + "        INNER JOIN editora ed\n"
                + "        ON l.Editora_id = ed.id\n"
                + "\n"
                + "	ORDER BY Emprestimo DESC, Reserva DESC, Titulo";

        try (Statement stmt = ConexaoRelatorio.getConexao().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object resul[] = new Object[9];
                resul[0] = rs.getLong(1); //id
                resul[1] = rs.getString(2); //Titulo
                resul[2] = rs.getString(3); // ISBN
                resul[3] = rs.getLong(4); // Edicao
                resul[4] = rs.getDate(5); // ano
                resul[5] = rs.getString(6); // Assunto
                resul[6] = rs.getString(7); // Editora
                resul[7] = rs.getLong(8); // qnt Emprestimo
                resul[8] = rs.getLong(9); // qnt Reserva             

                resultado.add(resul);
            }
        }
        ConexaoRelatorio.fecharConexao();
        return resultado;
    }

}
