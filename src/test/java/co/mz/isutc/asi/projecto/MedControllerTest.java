package co.mz.isutc.asi.projecto;

import co.mz.isutc.asi.projecto.controller.MedController;
import co.mz.isutc.asi.projecto.model.Medicamentos;
import co.mz.isutc.asi.projecto.repository.MedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MedControllerTest {

    // Mockar os objetos do repositório e do modelo
    @Mock
    private MedRepository medRepository;

    @Mock
    private Model model;

    // Injetar os objetos mockados no controlador que está sendo testado
    @InjectMocks
    private MedController medController;

    // Configurar os mocks antes de cada teste
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listMed_DeveRetornarViewFarmaciaList() {
        // Preparação: Mockar o repositório para retornar uma lista vazia quando findAll for chamado
        when(medRepository.findAll()).thenReturn(Collections.emptyList());

        // Ação: Chamar o método do controlador que lista os medicamentos
        String viewName = medController.listMed(model);

        // Verificação: Garantir que o nome da view correto é retornado e o modelo é atualizado
        assertEquals("farmacia/list", viewName); // Verificar se o nome da view retornada é "farmacia/list"
        verify(model, times(1)).addAttribute(eq("farmacia"), anyList()); // Verificar se uma lista vazia é adicionada ao modelo
        verify(medRepository, times(1)).findAll(); // Verificar se findAll() foi chamado uma vez
    }

    @Test
    void medCreateForm_DeveRetornarViewRegistar() {
        // Preparação: Nenhuma configuração necessária, pois não há interações externas
        // Ação: Chamar o método que prepara o formulário para registrar um novo medicamento
        String viewName = medController.medCreateForm(model);

        // Verificação: Garantir que o nome da view correto é retornado e o modelo é atualizado com um novo objeto Medicamentos
        assertEquals("farmacia/registar", viewName); // Verificar se o nome da view retornada é "farmacia/registar"
        verify(model, times(1)).addAttribute(eq("medicamentos"), any(Medicamentos.class)); // Verificar se um novo objeto Medicamentos é adicionado ao modelo
    }

    @Test
    void createMed_DeveRedirecionarParaFarmacia() {
        // Preparação: Criar um novo objeto Medicamentos para simular o envio do formulário
        Medicamentos medicamentos = new Medicamentos();

        // Ação: Chamar o método que cria um novo medicamento
        String viewName = medController.createMed(medicamentos);

        // Verificação: Garantir que, após criar um medicamento, redireciona para a lista "farmacia"
        assertEquals("redirect:/farmacia", viewName); // Verificar se o nome da view é "redirect:/farmacia"
        verify(medRepository, times(1)).save(medicamentos); // Verificar se o método save foi chamado uma vez
    }

    @Test
    void medShowForm_DeveRetornarViewEdit_QuandoIdForValido() {
        // Preparação: Configurar um ID de medicamento válido e mockar o repositório para retornar um medicamento existente
        Long id = 1L;
        Medicamentos medicamentos = new Medicamentos();
        when(medRepository.findById(id)).thenReturn(Optional.of(medicamentos));

        // Ação: Chamar o método que mostra o formulário do medicamento para edição
        String viewName = medController.medShowForm(id, model);

        // Verificação: Garantir que o nome da view correto é retornado e o modelo é atualizado com o medicamento
        assertEquals("farmacia/edit", viewName); // Verificar se o nome da view retornada é "farmacia/edit"
        verify(model, times(1)).addAttribute(eq("medicamentos"), eq(medicamentos)); // Verificar se o objeto Medicamentos correto é adicionado ao modelo
        verify(medRepository, times(1)).findById(id); // Verificar se o método findById do repositório foi chamado uma vez
    }

    @Test
    void medShowForm_DeveLançarExceção_QuandoIdForInvalido() {
        // Preparação: Configurar um ID de medicamento inválido e mockar o repositório para retornar vazio
        Long id = 1L;
        when(medRepository.findById(id)).thenReturn(Optional.empty());

        // Ação: Chamar o método e capturar a exceção
        try {
            medController.medShowForm(id, model);
        } catch (IllegalArgumentException ex) {
            // Verificação: Garantir que a exceção é lançada com a mensagem correta
            assertEquals("O ID inválido: " + id, ex.getMessage()); // Verificar se a mensagem da exceção corresponde à esperada
        }

        // Verificação: Garantir que o método findById foi chamado uma vez
        verify(medRepository, times(1)).findById(id);
    }

    @Test
    void updateMed_DeveRedirecionarParaFarmacia() {
        // Preparação: Criar um novo objeto Medicamentos com um ID definido
        Long id = 1L;
        Medicamentos medicamentos = new Medicamentos();
        medicamentos.setId(id);

        // Ação: Chamar o método que atualiza o medicamento
        String viewName = medController.updateMed(id, medicamentos);

        // Verificação: Garantir que, após atualizar, redireciona para a lista "farmacia"
        assertEquals("redirect:/farmacia", viewName); // Verificar se o nome da view é "redirect:/farmacia"
        verify(medRepository, times(1)).save(medicamentos); // Verificar se o método save foi chamado uma vez
    }

    @Test
    void deleteMed_DeveRedirecionarParaFarmacia() {
        // Preparação: Configurar um ID de medicamento para exclusão
        Long id = 1L;

        // Ação: Chamar o método que exclui o medicamento
        String viewName = medController.deleteMed(id);

        // Verificação: Garantir que, após a exclusão, redireciona para a lista "farmacia"
        assertEquals("redirect:/farmacia", viewName); // Verificar se o nome da view é "redirect:/farmacia"
        verify(medRepository, times(1)).deleteById(id); // Verificar se o método deleteById foi chamado uma vez
    }

    @Test
    void handleException_DeveRetornarViewErro() {
        // Preparação: Criar uma exceção para simular um erro
        Exception exception = new IllegalArgumentException("Exceção de Teste");

        // Ação: Chamar o método para tratar a exceção
        String viewName = medController.handleException(model, exception);

        // Verificação: Garantir que a view de erro correta é retornada e o modelo é atualizado com a mensagem de erro
        assertEquals("error", viewName); // Verificar se o nome da view retornada é "error"
        verify(model, times(1)).addAttribute(eq("error"), eq("Exceção de Teste")); // Verificar se a mensagem da exceção é adicionada ao modelo
    }
}
