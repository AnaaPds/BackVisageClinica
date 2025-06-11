package clinica.cosmetica.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import clinica.cosmetica.entities.Consulta;
import clinica.cosmetica.entities.Profissional;
import clinica.cosmetica.repository.ConsultaRepository;
import clinica.cosmetica.repository.ProfissionalRepository;

@Service
public class RelatorioConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    // Gera relatório PDF para um profissional
    public byte[] gerarPdfRelatorioPorProfissional(Long profissionalId) throws DocumentException {
        Optional<Profissional> profissionalOpt = profissionalRepository.findById(profissionalId);
        String nomeProfissional = profissionalOpt.map(Profissional::getNome).orElse("Profissional não encontrado");
        String especialidadeProfissional = profissionalOpt.map(Profissional::getEspecialidade).orElse("Especialidade não informada");

        List<Consulta> consultas = consultaRepository.findByProfissional_Id(profissionalId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Relatório de Consultas do Profissional: " + nomeProfissional));
        document.add(new Paragraph("Especialidade: " + especialidadeProfissional));
        document.add(new Paragraph("Total de Consultas: " + consultas.size()));
        document.add(new Paragraph(" "));

        for (Consulta consulta : consultas) {
            String data = (consulta.getDataHora() != null) ? consulta.getDataHora().toString() : "Data não informada";
            String paciente = (consulta.getPaciente() != null && consulta.getPaciente().getNome() != null)
                    ? consulta.getPaciente().getNome() : "Paciente não informado";
            String linha = "Data: " + data + ", Paciente: " + paciente;
            document.add(new Paragraph(linha));
        }

        document.close();
        return out.toByteArray();
    }
}
