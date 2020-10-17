package br.com.caelum.leilao.servico;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;

public class EncerradorDeLeilao {

    private int encerrados;
    private final RepositorioDeLeiloes dao;

    public EncerradorDeLeilao(RepositorioDeLeiloes dao) {
        this.dao = dao;
    }

    public void encerra() {
        List<Leilao> todosLeiloesCorrentes = dao.correntes();
        System.out.println("temos " + todosLeiloesCorrentes.size());
        for(Leilao leilao : todosLeiloesCorrentes) {
            if(comecouSemanaPassada(leilao)) {
                encerrados++;
                leilao.encerra();
                dao.atualiza(leilao);
            }
        }
    }

	public int getQuantidadeDeEncerrados() {
		return encerrados;
	}
	
	private boolean comecouSemanaPassada(Leilao leilao) {
		return diasEntre(leilao.getData(), Calendar.getInstance()) >= 7;
	}

	private int diasEntre(Calendar inicio, Calendar fim) {
		Calendar data = (Calendar) inicio.clone();
		int diasNoIntervalo = 0;
		while (data.before(fim)) {
			data.add(Calendar.DAY_OF_MONTH, 1);
			diasNoIntervalo++;
		}

		return diasNoIntervalo;
	}
}
