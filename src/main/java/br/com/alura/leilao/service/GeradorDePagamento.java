package br.com.alura.leilao.service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.hibernate.dialect.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Pagamento;

@Service
public class GeradorDePagamento {

	
	private PagamentoDao pagamentos;
	
	private Clock clock;
	
	// criar um construtor e colocar a injeção de dependências nele
	@Autowired
	public GeradorDePagamento(PagamentoDao pagamentos, Clock clock) {
		this.pagamentos = pagamentos;
		this.clock = clock;
	}	

	public void gerarPagamento(Lance lanceVencedor) {
		// o clock foi criado para ser possível testar qualquer data. Evitar ao máximo de utilizar métodos estáticos
		LocalDate vencimento = LocalDate.now(clock).plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, proximoDiaUtil(vencimento));
		this.pagamentos.salvar(pagamento);
	}

	private LocalDate proximoDiaUtil(LocalDate dataBase) {
		DayOfWeek diaDaSemana = dataBase.getDayOfWeek();
		if (diaDaSemana == DayOfWeek.SATURDAY) {
			return dataBase.plusDays(2);
		} else if (diaDaSemana == DayOfWeek.SUNDAY) {
			return dataBase.plusDays(1);
		}
		
		return dataBase;
	}

}
