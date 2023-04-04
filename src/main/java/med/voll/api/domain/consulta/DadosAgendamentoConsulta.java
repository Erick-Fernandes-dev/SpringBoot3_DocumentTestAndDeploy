package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(

        //Apelidando nossos atributos com JsonAlias
       @JsonAlias({"id_medico", "medico_id"}) Long idMedico,

        @NotNull
       @JsonAlias({"id_paciente", "paciente_id"}) Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade

        ) {
}
