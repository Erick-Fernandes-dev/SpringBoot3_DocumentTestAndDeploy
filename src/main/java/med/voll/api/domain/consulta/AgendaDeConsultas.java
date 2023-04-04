package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    // Agendar Consulta
    public void agendar(DadosAgendamentoConsulta dados) {

        if (!this.pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("ID do paciente informado não existe");
        }

        if (dados.idMedico() != null && !this.medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("ID do médico informado não existe");
        }


        var paciente = this.pacienteRepository.findById(dados.idPaciente()).get();
//        var medico = this.medicoRepository.findById(dados.idMedico()).get();
        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);


        this.consultaRepository.save(consulta);

    }

    // Escolher médico
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw  new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }



        return this.medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());


    }

    // Cancelar Consulta
    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!this.consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("ID da consulta informado não existe");
        }

        var consulta = this.consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivoCancelamento());
    }
}
