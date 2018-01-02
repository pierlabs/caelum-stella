package br.com.caelum.stella.boleto.bancos;

import static br.com.caelum.stella.boleto.utils.StellaStringUtils.leftPadWithZeros;

import java.net.URL;

import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.bancos.gerador.GeradorDeDigitoSantander;

/**
 * Gerar Boleto do Banco BMG
 * 
 */
public class BMG extends AbstractBanco {

	private static final long serialVersionUID = 1L;

	private final static String NUMERO_BMG = "318";
	
	private static final String DIGITO_NUMERO_BMG = "2";
	
     private static final String CODIGO_BOLETO_REGISTRADO = "02";
     
     private static final String CODIGO_BOLETO_REJEITADO = "03";

	private GeradorDeDigitoSantander gdivBMG = new GeradorDeDigitoSantander();

	@Override
	public String geraCodigoDeBarrasPara(Boleto boleto) {
		Beneficiario beneficiario = boleto.getBeneficiario();
		StringBuilder campoLivre = new StringBuilder("");
		campoLivre.append(beneficiario.getAgenciaFormatada());
		campoLivre.append(leftPadWithZeros(beneficiario.getCodigoBeneficiario(), 10));
		//campoLivre.append(beneficiario.getDigitoCodigoBeneficiario());
		campoLivre.append("0");
		campoLivre.append(getNossoNumeroFormatado(beneficiario));
		return new CodigoDeBarrasBuilder(boleto).comCampoLivre(campoLivre);
	}
	
	@Override
	public URL getImage() {
		String pathDoArquivo = "/br/com/caelum/stella/boleto/img/%s.png";
		String imagem = String.format(pathDoArquivo, NUMERO_BMG);
		return getClass().getResource(imagem);
	}

	@Override
	public String getNumeroFormatado() {
		return NUMERO_BMG;
	}

	@Override
	public String getCarteiraFormatado(Beneficiario beneficiario) {
		return leftPadWithZeros(beneficiario.getCarteira(), 3);
	}

	@Override
	public String getCodigoBeneficiarioFormatado(Beneficiario beneficiario) {
		return leftPadWithZeros(beneficiario.getCodigoBeneficiario(), 7);
	}

	@Override
	public String getNossoNumeroFormatado(Beneficiario beneficiario) {
		return leftPadWithZeros(beneficiario.getNossoNumero(), 10);
	}

	@Override
	public String getNumeroFormatadoComDigito() {
		StringBuilder builder = new StringBuilder();
		builder.append(NUMERO_BMG).append("-");
		return builder.append(DIGITO_NUMERO_BMG).toString();
	}

	@Override
	public String getAgenciaECodigoBeneficiario(Beneficiario beneficiario) {
		StringBuilder builder = new StringBuilder();
		builder.append(leftPadWithZeros(beneficiario.getAgencia(), 5));
		builder.append("/").append(getNumeroConvenioFormatado(beneficiario));
		return builder.toString();
	}

	@Override
	public String getNossoNumeroECodigoDocumento(Boleto boleto) {
		Beneficiario beneficiario = boleto.getBeneficiario();
		
		String nossoNumero = getNossoNumeroFormatado(beneficiario);
		StringBuilder builder = new StringBuilder();
		builder.append(nossoNumero.substring(0, 12));
		builder.append("-").append(nossoNumero.substring(12));
		return  builder.toString();
	}
 
	@Override
	public GeradorDeDigitoSantander getGeradorDeDigito() {
		return gdivBMG;
	}
	
	public String getNumeroConvenioFormatado(Beneficiario beneficiario) {
		return leftPadWithZeros(beneficiario.getNumeroConvenio(), 7);
	}
	
     /**
      * Método para cálculo do dígito verificador do campo Nosso Número.
      * 
      * @param beneficiario
      * @return String
      */
     @Override
     public String getNossoNumeroComDigitoVerificador(Beneficiario beneficiario) {
          
          return beneficiario.getNossoNumero();
     }

     @Override
     public String getCodigoBoletoRegistrado() {
     
          return CODIGO_BOLETO_REGISTRADO;
     }
     
     @Override
     public String getCodigoBoletoRejeitado() {
     
          return CODIGO_BOLETO_REJEITADO;
     }
}
