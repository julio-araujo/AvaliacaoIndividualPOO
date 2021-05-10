package ObjetoProcesso;

public class PessoaFisica extends Processo{
    private String cpf;

    public PessoaFisica(String reu, String autor, String numeroProcesso, String advogadoAutor, String advogadoReu, String cpf){
        super(reu, autor, numeroProcesso, advogadoAutor, advogadoReu);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
