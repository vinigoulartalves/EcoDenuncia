# Etapa 1 — Base do projeto EcoDenuncia

## 1) Dependências necessárias (Gradle)

As dependências desta etapa ficam em dois arquivos:

- `gradle/libs.versions.toml` (catálogo de versões e aliases)
- `app/build.gradle.kts` (declaração de uso no módulo `app`)

### Tecnologias solicitadas

- Jetpack Compose
- Material 3
- Navigation Compose
- Room
- Lifecycle ViewModel

## 2) Estrutura de pacotes sugerida

```text
br.com.ecodenuncia
├── data
│   ├── local
│   │   ├── dao
│   │   ├── database
│   │   └── converter
│   └── repository
├── domain
│   ├── model
│   └── usecase
├── ui
│   ├── components
│   ├── screens
│   ├── navigation
│   └── theme
└── viewmodel
```

> Observação: para esta etapa, apenas os modelos de domínio (`Denuncia` e `StatusDenuncia`) são necessários.

## 3) Entidade `Denuncia`

Arquivo recomendado:

- `app/src/main/java/br/com/ecodenuncia/model/Denuncia.kt`

## 4) Enum de status da denúncia

Arquivo recomendado:

- `app/src/main/java/br/com/ecodenuncia/model/StatusDenuncia.kt`

## 5) Em quais arquivos cada item deve ficar

- Dependências e versões: `gradle/libs.versions.toml`
- Dependências do app: `app/build.gradle.kts`
- Entidade `Denuncia`: `app/src/main/java/br/com/ecodenuncia/model/Denuncia.kt`
- Enum `StatusDenuncia`: `app/src/main/java/br/com/ecodenuncia/model/StatusDenuncia.kt`
- Estrutura de pacotes (referência): este documento `docs/etapa1-base-projeto.md`

## Escopo desta etapa

- Sem criação de telas
- Sem criação de ViewModel
- Sem criação de navegação
