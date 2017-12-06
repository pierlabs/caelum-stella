package br.com.caelum.stella.boleto.bancos;

import static br.com.caelum.stella.boleto.utils.StellaStringUtils.leftPadWithZeros;

import java.net.URL;

import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.bancos.gerador.GeradorDeDigitoBancoAgiplan;
import br.com.caelum.stella.boleto.bancos.gerador.GeradorDeDigitoSantander;

public class BancoAgiplan extends AbstractBanco{


	private static final long serialVersionUID = 1L;
	private static final String NUMERO_BANCO_AGIPLAN = "121";
	private static final String DIGITO_NUMERO_BANCO_AGIPLAN = "1";
	private GeradorDeDigitoBancoAgiplan gdivBancoAgiplan = new GeradorDeDigitoBancoAgiplan();

	@Override
	public String getNumeroFormatado() {
		return NUMERO_BANCO_AGIPLAN;
	}

	@Override
	public URL getImage() {
		String pathDoArquivo = "/br/com/caelum/stella/boleto/img/%s.png";
		String imagem = String.format(pathDoArquivo, NUMERO_BANCO_AGIPLAN);
		return getClass().getResource(imagem);
	}

	@Override
	public String geraCodigoDeBarrasPara(Boleto boleto) {
		Beneficiario beneficiario = boleto.getBeneficiario();
		StringBuilder campoLivre = new StringBuilder("");
		campoLivre.append(beneficiario.getAgenciaFormatada());
		campoLivre.append(leftPadWithZeros(beneficiario.getCodigoBeneficiario(), 10));
		campoLivre.append("0");
		campoLivre.append(getNossoNumeroComDigitoVerificador(beneficiario));
		return new CodigoDeBarrasBuilder(boleto).comCampoLivre(campoLivre);
	}

	@Override
	public String getCodigoBeneficiarioFormatado(Beneficiario beneficiario) {
		return leftPadWithZeros(beneficiario.getCodigoBeneficiario(), 7);
	}

	@Override
	public String getCarteiraFormatado(Beneficiario beneficiario) {
		return leftPadWithZeros(beneficiario.getCarteira(), 2);
	}

	@Override
	public String getNossoNumeroFormatado(Beneficiario beneficiario) {
		return leftPadWithZeros(beneficiario.getNossoNumero(), 10);
	}

	@Override
	public String getNumeroFormatadoComDigito() {
		StringBuilder builder = new StringBuilder();
		builder.append(NUMERO_BANCO_AGIPLAN).append("-");
		return builder.append(DIGITO_NUMERO_BANCO_AGIPLAN).toString();
	}

	@Override
	public String getNossoNumeroComDigitoVerificador(Beneficiario beneficiario) {
		return getGeradorDeDigito().calculaDVNossoNumero(getCarteiraFormatado(beneficiario)+beneficiario.getNossoNumero());
	}
	
	@Override
	public GeradorDeDigitoBancoAgiplan getGeradorDeDigito() {
		return gdivBancoAgiplan;
	}

}
