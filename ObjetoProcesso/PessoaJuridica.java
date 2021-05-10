package ObjetoProcesso;

public class PessoaJuridica extends Processo{
    private String cnpj;

    public PessoaJuridica(String reu, String autor, String numeroProcesso, String advogadoAutor, String advogadoReu, String cnpj){
        super(reu, autor, numeroProcesso, advogadoAutor, advogadoReu);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }
}
