# Tugas Praktikum Minggu 10 — Notes App

Implementasi dependency injection dan testing untuk Notes App dengan Koin, SQLDelight, MockK, Turbine, dan Compose UI tests.

## Checklist tugas
- Koin DI dengan module data dan viewModel
- Unit test `NoteRepository` (lebih dari 5 kasus)
- Unit test `NoteViewModel` dengan MockK
- Flow test dengan Turbine
- UI test `NotesScreen`
- Persiapan coverage untuk business logic

## Cara menjalankan test
Jalankan semua JVM test:

```bash
./gradlew :composeApp:jvmTest
```

Generate coverage report:

```bash
./gradlew :composeApp:koverHtmlReport
```

Jika ingin menjalankan test tertentu dari IDE:
- `SqlDelightNoteRepositoryTest`
- `NoteViewModelTest`
- `NoteViewModelFlowTest`
- `NotesScreenTest`

## Daftar test case

### NoteRepository
1. Menampilkan 3 note seed awal
2. Menambahkan note baru ke database
3. Mengubah note yang sudah ada
4. Men-toggle status favorit
5. Menghapus note
6. Mencari note berdasarkan kata kunci

### NoteViewModel (MockK)
1. `setSearchQuery()` mengubah query
2. `clearSearch()` mereset query
3. `addNote()` memanggil repository
4. `updateNote()` memanggil repository
5. `deleteNote()` memanggil repository
6. `toggleFavorite()` memanggil repository

### Flow test (Turbine)
1. `uiState` menghasilkan `Content` saat note tersedia
2. `uiState` menghasilkan `Empty("No result")` saat hasil pencarian kosong

### UI test NotesScreen
1. Menampilkan empty state saat tidak ada note
2. Menampilkan daftar note saat data tersedia
3. Search memfilter daftar dan tombol clear mengembalikan list

## Catatan coverage
Setelah test berhasil dijalankan, buka hasil HTML coverage dari task `:composeApp:koverHtmlReport`, lalu ambil screenshot-nya untuk dimasukkan ke README sebelum dikumpulkan.
