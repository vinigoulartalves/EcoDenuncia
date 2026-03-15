# Etapa 3 — ViewModel da denúncia

## O que foi implementado

1. **DenunciaViewModel**  
   Arquivo: `app/src/main/java/br/com/ecodenuncia/viewmodel/DenunciaViewModel.kt`

2. **Estado temporário do formulário (`DenunciaFormState`)**  
   Arquivo: `app/src/main/java/br/com/ecodenuncia/viewmodel/DenunciaViewModel.kt`

3. **Funções da ViewModel**  
   Arquivo: `app/src/main/java/br/com/ecodenuncia/viewmodel/DenunciaViewModel.kt`
   - `atualizarCampoFormulario(...)`
   - `salvarDenuncia(...)`
   - `denuncias` (listagem em `StateFlow`)
   - `buscarDenunciaPorId(...)`
   - `atualizarStatusParaEnviada(...)`
   - `excluirDenuncia(id: Long)`

## Compatibilidade com Room

A ViewModel usa apenas operações já existentes no repositório (`inserir`, `listar`, `buscarPorId`, `buscarPorIdUmaVez`, `atualizar`, `excluir`), mantendo compatibilidade com DAO/Room sem dependência de telas.
