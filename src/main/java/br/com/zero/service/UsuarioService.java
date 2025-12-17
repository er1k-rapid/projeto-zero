package br.com.zero.service;

import br.com.zero.dao.UsuarioDAO;
import br.com.zero.dto.UsuarioDTO;
import br.com.zero.model.Usuario;
import java.sql.SQLException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;
import br.com.zero.util.MailSender;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(Usuario usuario) throws SQLException {
        String senhaHash = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(senhaHash);
        usuarioDAO.inserir(usuario);
    }
    
    public void cadastrarComVerificacao(Usuario usuario) throws Exception {

        String senhaHash = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(senhaHash);

        String token = UUID.randomUUID().toString();
        usuario.setEmailToken(token);
        usuario.setEmailVerificado(false);
        usuario.setStatus("Ativo");

        usuarioDAO.inserir(usuario);

        MailSender mail = new MailSender(
            "sandbox.smtp.mailtrap.io",
            587,
            "4feff7207a7a7d",
            "f406483e712737",
            true
        );

        String link = "http://localhost:8080/verificar-email?token=" + token;

        String html =
        		"<!DOCTYPE html>" +
        		"<html lang='pt-BR'>" +
        		"<head>" +
        		"    <meta charset='UTF-8'>" +
        		"    <title>Confirmação de Cadastro</title>" +
        		"</head>" +
        		"<body style='margin:0; padding:0; background-color:#f3f4f6; font-family:Arial, Helvetica, sans-serif;'>" +

        		"    <table width='100%' cellpadding='0' cellspacing='0' style='background-color:#f3f4f6; padding:40px 0;'>" +
        		"        <tr>" +
        		"            <td align='center'>" +

        		"                <table width='600' cellpadding='0' cellspacing='0' style='background-color:#ffffff; border-radius:16px; box-shadow:0 10px 30px rgba(0,0,0,0.1); overflow:hidden;'>" +

        		"                    <tr>" +
        		"                        <td style='padding:0 48px 24px 48px;'>" +
        		"                            <h2 style='margin:0 0 12px 0; font-size:24px; color:#1f2937;'>Confirmação de Cadastro</h2>" +
        		"                            <p style='margin:0; font-size:14px; color:#6b7280; line-height:1.6;'>" +
        		"                                Olá <strong style='color:#111827;'>" + usuario.getNome() + "</strong>,<br><br>" +
        		"                                Para concluir seu cadastro e acessar a plataforma, confirme seu e-mail clicando no botão abaixo." +
        		"                            </p>" +
        		"                        </td>" +
        		"                    </tr>" +

        		"                    <tr>" +
        		"                        <td align='center' style='padding:32px;'>" +
        		"                            <a href='" + link + "' " +
        		"                               style='display:inline-block; padding:14px 32px; background-color:#021926; color:#ffffff; text-decoration:none; border-radius:12px; font-size:14px; font-weight:600;'>" +
        		"                                Confirmar e-mail" +
        		"                            </a>" +
        		"                        </td>" +
        		"                    </tr>" +

        		"                    <tr>" +
        		"                        <td style='padding:0 48px 40px 48px;'>" +
        		"                            <p style='margin:0; font-size:12px; color:#9ca3af; line-height:1.6;'>" +
        		"                                Se você não criou uma conta, pode ignorar este e-mail com segurança.<br>" +
        		"                                Este link pode expirar por motivos de segurança." +
        		"                            </p>" +
        		"                        </td>" +
        		"                    </tr>" +

        		"                </table>" +

        		"            </td>" +
        		"        </tr>" +
        		"    </table>" +

        		"</body>" +
        		"</html>";

        mail.sendHtml(
            "no-reply@zero.com.br",
            usuario.getEmail(),
            "Confirme seu cadastro",
            html
        );
    }
    
    public boolean verificarEmail(String token) throws SQLException {

        Usuario usuario = usuarioDAO.buscarPorEmailToken(token);
        if (usuario == null) return false;

        usuario.setEmailVerificado(true);
        usuario.setEmailToken(null);
        usuario.setStatus("Ativo");

        usuarioDAO.atualizar(usuario);
        return true;
    }

    public Usuario autenticar(String email, String senha) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario != null && BCrypt.checkpw(senha, usuario.getSenha())) {

            if (Boolean.FALSE.equals(usuario.getEmailVerificado())) {
                return null; 
            }

            return usuario;
        }
        return null;
    }

    public Usuario buscarPorId(int id, int empresaId) throws SQLException {
        return usuarioDAO.buscarPorId(id, empresaId);
    }

    public List<UsuarioDTO> listar(int empresaId, String nome) throws SQLException {
        return usuarioDAO.listar(empresaId, nome);
    }

    public void atualizar(Usuario usuario) throws SQLException {
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            String senhaHash = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
            usuario.setSenha(senhaHash);
        } else {
            usuario.setSenha(null); 
        }
        usuarioDAO.atualizar(usuario);
    }

    public void deletar(int id, int empresaId) throws SQLException {
        usuarioDAO.deletar(id, empresaId);
    }
}
