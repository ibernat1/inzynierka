# -*- coding: utf-8 -*-
from flask import Flask, send_file, request, jsonify
import os

app = Flask(__name__)

# Ścieżka do pliku modelu .tflite
MODEL_PATH = os.path.join(os.path.dirname(os.path.dirname(__file__)), 'modele', 'model.tflite')

@app.route('/download-model', methods=['GET'])
def download_model():
    try:
        # Sprawdź opcjonalne parametry (jeśli wymagane)
        client_info = request.args.get('client', 'unknown')
        print(f"Model requested by client: {client_info}")

        # Wysyłanie pliku modelu
        return send_file(
            MODEL_PATH,
            as_attachment=True,
            download_name="model.tflite",
            mimetype="application/octet-stream"
        )
    except Exception as e:
        # Obsługa błędów
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    # Uruchom serwer Flask
    app.run(host='0.0.0.0', port=5000)