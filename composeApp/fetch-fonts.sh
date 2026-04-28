#!/usr/bin/env bash
# Скачивает шрифты Noto Sans (variable) для Compose Multiplatform JS-таргета.
# Использует канонический репозиторий google/fonts (OFL-лицензия).
#
# Variable-фонт — один файл на язык, поддерживает все веса (Regular, SemiBold).
#
# Использование:
#   cd composeApp && ./fetch-fonts.sh

set -euo pipefail

DIR="$(dirname "$0")/src/commonMain/composeResources/font"
mkdir -p "$DIR"
cd "$DIR"

# google/fonts main:  https://github.com/google/fonts/tree/main/ofl/<family>
RAW="https://raw.githubusercontent.com/google/fonts/main/ofl"
JSDELIVR="https://cdn.jsdelivr.net/gh/google/fonts@main/ofl"

dl() {
    # Пробуем raw.githubusercontent, при ошибке — jsdelivr.
    local out="$1" path="$2"
    echo "→ $out"
    curl -fsSL --globoff -o "$out" "$RAW/$path" \
        || curl -fsSL --globoff -o "$out" "$JSDELIVR/$path"
}

# Латиница + кириллица (RU/EN). Variable: оси wdth, wght.
dl "NotoSans-Regular.ttf"        "notosans/NotoSans%5Bwdth,wght%5D.ttf"

# Арабский. Variable: wdth, wght.
dl "NotoSansArabic-Regular.ttf"  "notosansarabic/NotoSansArabic%5Bwdth,wght%5D.ttf"

# Упрощённый китайский. Variable: только wght (~9 МБ — может занять минуту).
dl "NotoSansSC-Regular.ttf"      "notosanssc/NotoSansSC%5Bwght%5D.ttf"

echo
echo "Done. Files in: $DIR"
ls -lah
