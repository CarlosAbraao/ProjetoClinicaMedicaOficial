package com.mballem.curso.security.service;

import com.mballem.curso.security.datatables.Datatables;
import com.mballem.curso.security.datatables.DatatablesColunas;
import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;


    @Autowired
    private Datatables datatables;

    /*alguns metodos de funcionarios foram obitidos */

    @Transactional(readOnly = true)
    public Usuario BuscarPorEmail(String email){
        return repository.findByEmail(email);
    }

    @Override @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = BuscarPorEmail(username);

        return new User(
                usuario.getEmail(),
               usuario.getSenha(),
                AuthorityUtils.createAuthorityList(getAtuthorities(usuario.getPerfis()))

        );
    }

    private String[] getAtuthorities(List<Perfil> perfis) {
        String[] authorities = new String[perfis.size()];
        for (int i = 0; i < perfis.size(); i++) {
            authorities[i] = perfis.get(i).getDesc();

        }
        return authorities;
    }
    @Transactional(readOnly = true)
    public Map<String, Object> buscarTodos(HttpServletRequest request) {

        datatables.setRequest(request);
        datatables.setColunas(DatatablesColunas.USUARIOS);
        Page<Usuario> page = datatables.getSearch().isEmpty()

                ? repository.findAll((datatables.getPageable()))
                : repository.findByEmailOrPerfil(datatables.getSearch(), datatables.getPageable());

        return datatables.getResponse(page);
    }

    @Transactional(readOnly = false)
    public void savarUsuario(Usuario usuario) {
        String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(crypt);
        repository.save(usuario);
    }
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {

        return repository.findById(id).get();
    }


    @Transactional(readOnly = true)
    public Usuario buscarPorIdPerfil(Long usuarioId, Long[] perfisId) {
        return repository.findByIdAndPerfis(usuarioId, perfisId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inexistente."));
    }
}