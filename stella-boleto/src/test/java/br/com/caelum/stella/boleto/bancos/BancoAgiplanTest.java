package br.com.caelum.stella.boleto.bancos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Pagador;

public class BancoAgiplanTest {
	
	private Boleto boleto;
	private BancoAgiplan banco = new BancoAgiplan();
	private Beneficiario beneficiario;
	
	
	@Test
	public void testLinhaDoBancoAgiplan() {
		 Datas datas = Datas.novasDatas().comDocumento(20, 03, 2014)
		            .comProcessamento(20, 03, 2014).comVencimento(10, 04, 2014); 
		
		this.beneficiario = Beneficiario.novoBeneficiario().comNomeBeneficiario("BANCO DO TESTE.")
				.comAgencia("1").comCarteira("11").comCodigoBeneficiario("1")
				.comNossoNumero("0000097019").comDigitoNossoNumero("6")
				.comDocumento("10004513000000").comNumeroConvenio("1");
		
		Pagador pagador = Pagador.novoPagador().comNome("HOMEM DO TESTE")
				.comDocumento("01891300000");
	    
	    this.boleto = Boleto.novoBoleto().comDatas(datas).comBeneficiario(beneficiario)
	    	.comBanco(banco).comPagador(pagador).comValorBoleto(5472.9400)
	    	.comNumeroDoDocumento("1102497019").comEspecieDocumento("DS")
	    	.comAceite(false).comCodigoEspecieMoeda(9)
	    	.comInstrucoes("TESTE");
		 
		this.boleto = this.boleto.comBanco(this.banco);
		GeradorDeLinhaDigitavel gerador = new GeradorDeLinhaDigitavel();
		String codigoDeBarras = boleto.getBanco().geraCodigoDeBarrasPara(this.boleto);
		String linha = "12190.00005  00000.010009  00970.191144  7  60290000547294";
		assertEquals(linha, gerador.geraLinhaDigitavelPara(codigoDeBarras,this.banco));
	}


	@Test
	public void testGetImage() {
		assertNotNull(this.banco.getImage());
	}
}
