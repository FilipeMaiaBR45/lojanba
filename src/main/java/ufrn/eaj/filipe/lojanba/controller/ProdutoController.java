package ufrn.eaj.filipe.lojanba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufrn.eaj.filipe.lojanba.model.Produto;
import ufrn.eaj.filipe.lojanba.service.FileStorageService;
import ufrn.eaj.filipe.lojanba.service.ProdutoService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ProdutoController {


    ProdutoService service;
    FileStorageService fileStorageService;

    List<Produto> carrinho = new ArrayList<Produto>();

    @Autowired
    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    public void setService(ProdutoService service){
        this.service = service;
    }

    @RequestMapping({"/", "/index"})
    public String getHome(Model model,  HttpServletResponse response){

        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");
        String currentTime = sdf.format(data);
        Cookie c = new Cookie("visita", currentTime);
        c.setMaxAge(86400);
        response.addCookie(c);

        List<Produto> produtoList = service.findAll();

        model.addAttribute("produtoList", produtoList);

        return "index";
    }

    @RequestMapping("/cadastro")
    public String getFormCadastro(Model model){
        Produto produto = new Produto();
    model.addAttribute("produto", produto);
    return "cadastrar";
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String doSalvar(@ModelAttribute @Valid Produto produto, Errors errors, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        if (errors.hasErrors()){
            return "cadastrar";
        }else{

            produto.setImageUri(UUID.randomUUID().toString() + file.getOriginalFilename());
            service.save(produto);
            fileStorageService.save(file, produto);

            redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso");
            return "redirect:/";
        }
    }

    @RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
    public String getAdmin(Model model) {
        var produtoList = service.findAll();
        model.addAttribute("produtoList", produtoList);
        return "admin";
    }

    @RequestMapping("/deletar/{id}")
    public String doDelete(@PathVariable(name = "id") Long id) {
        service.deletarProduto(id);
        return "redirect:/admin";
    }

    @RequestMapping("/editar/{id}")
    public ModelAndView getFormEdicao(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("editar");
        Produto produto = service.findById(id);
        modelAndView.addObject("produto", produto);
        return modelAndView;
    }

    @RequestMapping("/adicionarCarrinho/{id}")
    public String getCarrinho(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<Produto>());
        }
        ArrayList<Produto> produtosCarrinho = (ArrayList<Produto>) session.getAttribute("cart");

        produtosCarrinho.add(service.findById(id));
        System.out.println("");

        return "redirect:/";
    }


}
