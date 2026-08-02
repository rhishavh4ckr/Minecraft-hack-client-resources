# JARVIS Local Coding Assistant

## Project structure

```text
jarvis_assistant/
├── backend.py
├── config.json
├── config.example.json
├── requirements.txt
├── memory.sqlite3          # created on first run (ignored by git)
└── static/
    ├── index.html
    ├── style.css
    └── app.js
```

This is a deliberately small custom ReAct-style HTTP agent rather than a heavy framework. The model is only used for conversational coding help; deterministic tools are separately exposed and never selected by arbitrary shell text.

## Windows installation

1. Install 64-bit Python 3.11 or newer and [Ollama](https://ollama.com), both free/open source software. Install Git separately if you want git tools.
2. Create the project directory, then copy `config.example.json` to `config.json` if needed. Edit `config.json`: set `projects_folder` to an existing directory such as `C:\Projects`, and add only trusted absolute executable paths to `allowed_apps`.
3. In PowerShell:

```powershell
cd C:\path\to\jarvis_assistant
py -3.11 -m venv .venv
.\.venv\Scripts\Activate.ps1
python -m pip install -r requirements.txt
ollama pull qwen2.5-coder:3b
python -m uvicorn backend:app --host 127.0.0.1 --port 8000
```

4. Open `http://127.0.0.1:8000` in Edge or Chrome. Browser speech recognition and speech synthesis are used for the Listen button; the server remains local.

## Hardware fit

The default `qwen2.5-coder:3b` is intentionally below the requested 4B/8B range: it is the safer first model for a 4-core i5-6500T, 16 GB RAM, and 8 GB Polaris VRAM. A 4B quantized model is a reasonable upgrade if latency is acceptable; an 8B quantized model may run partly in system RAM but is not the lightweight default and should not be assumed fast on this CPU/GPU. Ollama will use available acceleration where supported and otherwise CPU. The request context is capped at 4096 tokens and history at 12 messages to control memory.

## Security model

- Binds only to loopback; no cloud endpoint is used.
- File writes are confined to the configured projects folder after path resolution.
- Tools are fixed argument arrays executed with `shell=False`, a timeout, and output limits. There is no generic shell endpoint.
- Application launch requires an explicit whitelist entry.
- The assistant cannot edit its own code or expand its tools. SQLite stores conversation history locally.

The included commands are `git_status`, `git_diff`, `tests`, and `format_python`; call them from a future UI/tool control only after explicit user intent. Configuration may add fixed, reviewed argument arrays, but should never contain user-provided command strings.
