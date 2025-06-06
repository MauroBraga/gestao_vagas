package br.com.mrb.gestao_vagas.modules.company.controllers;

import br.com.mrb.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.mrb.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.mrb.gestao_vagas.modules.company.repository.CompanyRepository;
import br.com.mrb.gestao_vagas.modules.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    public void should_be_able_to_create_job() throws Exception {

        var company = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION")
                .email("email@company.com")
                .password("1234567890")
                .username("COMPANY_USERNAME")
                .name("COMPANY_NAME").build();

        company = companyRepository.saveAndFlush(company);

        var createdJob =CreateJobDTO.builder()
                .level("LEVEL_TEST")
                .benefits("BENEFITS_TEST")
                        .description("DESCRIPTION_TEST").build();

        mvc.perform(MockMvcRequestBuilders.post("/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createdJob))
                .header("Authorization", TestUtils.generateTokenCompany(company.getId()))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_not_be_able_to_create_job_if_company_not_fout() throws Exception {

        var createdJob =CreateJobDTO.builder()
                .level("LEVEL_TEST")
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST").build();

        mvc.perform(MockMvcRequestBuilders.post("/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createdJob))
                .header("Authorization", TestUtils.generateTokenCompany(UUID.randomUUID()))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
