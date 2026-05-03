# Nama : Abi Sholihan
# NIM  : 123140192 

# Tugas Kesembilan — Integrasi AI API

Project ini menambahkan fitur **AI Text Summarizer** pada aplikasi Kotlin Multiplatform.

## Fitur
- Input teks bebas
- Ringkasan otomatis menggunakan **Gemini API**
- Loading state saat request berjalan
- Handling error untuk API key, network, rate limit, dan server error

## Struktur singkat
- `data/ai` — service dan repository AI
- `presentation/summarizer` — ViewModel dan UI Compose
- `config` — penyimpanan API key via `local.properties`

## Setup API Key
Tambahkan baris ini ke file `local.properties` di root project:

```properties
GEMINI_API_KEY=isi_api_key_kamu
```

## Menjalankan aplikasi Android
Buka project di Android Studio lalu jalankan module `composeApp`.

## Cara pakai
1. Tempel teks ke kotak input.
2. Tekan tombol **Ringkas**.
3. Tunggu hasil ringkasan dari Gemini.
