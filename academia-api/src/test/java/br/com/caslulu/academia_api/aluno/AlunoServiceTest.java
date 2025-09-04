package br.com.caslulu.academia_api.aluno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.caslulu.academia_api.inscricao.InscricaoRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest{
  @Mock
  private AlunoRepository alunoRepository;

  @Mock
  private InscricaoRepository inscricaoRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AlunoService alunoService;

  private Aluno alunoSalvo;

  @BeforeEach
  void setup(){
    this.alunoSalvo = new Aluno("Joao Silva", "joao@email.com");
  }

  @Test
  void criarAlunoSenhaCriptoERole() throws Exception{
    alunoSalvo.setSenha("12345");

    when(passwordEncoder.encode("12345")).thenReturn("senhaCriptografada");
    when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Aluno alunoResultado = alunoService.criar(alunoSalvo);
    assertThat(alunoResultado).isNotNull();
    assertThat(alunoResultado.getRole()).isEqualTo("ALUNO");
    assertThat(alunoResultado.getSenha()).isEqualTo("senhaCriptografada");

    verify(alunoRepository).save(any(Aluno.class));
    verify(passwordEncoder).encode("12345");
  }
  
  @Test
  void buscarAlunoExistente() throws Exception{
    Long id = 1L;
    alunoSalvo.setSenha("12345");
    when(passwordEncoder.encode("12345")).thenReturn("senhaCriptografada");
    when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));
    Aluno alunoResultado = alunoService.criar(alunoSalvo);
    assertThat(alunoResultado.getNome()).isEqualTo("Joao Silva");
    assertThat(alunoResultado.getEmail()).isEqualTo("joao@email.com");
    assertThat(alunoResultado.getNome()).isEqualTo("Joao Silva");
    assertThat(alunoResultado.getSenha()).isEqualTo("senhaCriptografada");
  }

  @Test
  void atualizarAlunoExistente() throws Exception{
    Long id = 1L;

    Aluno alunoNovasInfos = new Aluno("Joao Pedro", "novoemail@email.com");

    when(alunoRepository.findById(id)).thenReturn(Optional.of(this.alunoSalvo));
    when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation ->invocation.getArgument(0));
    AlunoResponseDTO alunoAtualizado = alunoService.atualizar(id, alunoNovasInfos);
    assertThat(alunoAtualizado.nome()).isEqualTo(alunoNovasInfos.getNome());
    assertThat(alunoAtualizado.email()).isEqualTo(alunoNovasInfos.getEmail());
  }

  @Test
  void deletarAlunoExistente() throws Exception{
    Long id = 1L;
    when(alunoRepository.existsById(id)).thenReturn(true);

    assertDoesNotThrow(() -> {
      alunoService.deletar(id);
    });
  }

  @Test
  void criarAlunoSemSenha() throws Exception{
    Exception exception = assertThrows(Exception.class, () -> {
      alunoService.criar(alunoSalvo);
    });
    
      String mensagemEsperada = "Nome, Email e senha sao obrigatorios";
    String mensagemRecebida = exception.getMessage();
    assertThat(mensagemRecebida).isEqualTo(mensagemEsperada);
  }

  @Test
  void verAlunoInexistente() throws Exception{
    Long id = 30L;
    Exception exception = assertThrows(Exception.class, ()-> {
      alunoService.buscarPorId(id);
    });
      String mensagemEsperada = "Aluno não encontrado";
      String mensagemRecebida = exception.getMessage();
      assertThat(mensagemEsperada).isEqualTo(mensagemRecebida);
  }

  @Test
  void atualizarAlunoInexistente() throws Exception{
    Long id = 30L;
    Exception exception = assertThrows(Exception.class, ()-> {
      alunoService.atualizar(id, alunoSalvo);
    });
      String mensagemEsperada = "Não foi possivel encontrar o aluno para atualizar";
      String mensagemRecebida = exception.getMessage();
      assertThat(mensagemEsperada).isEqualTo(mensagemRecebida);
  }

  @Test
  void deletarAlunoInexistente() throws Exception{
    Long id = 30L;
    Exception exception = assertThrows(Exception.class, ()-> {
      alunoService.deletar(id);
    });
      String mensagemEsperada = "Aluno nao encontrado para o ID: " + id;
      String mensagemRecebida = exception.getMessage();
      assertThat(mensagemEsperada).isEqualTo(mensagemRecebida);
 }

}
