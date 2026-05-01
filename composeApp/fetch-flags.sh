#!/usr/bin/env bash
# Скачивает PNG-флаги от Twemoji (72x72) в composeResources/files/flags/.
#
# Использует репозиторий jdecked/twemoji (форк с актуальными флагами после
# того, как Twitter закрыл оригинальный твеп-репо).
#
# Twemoji кодирует флаги как имя файла из codepoints двух regional indicators,
# например 🇺🇸 = U+1F1FA + U+1F1F8 → "1f1fa-1f1f8.png".
#
# Перебираем все комбинации AA..ZZ (676), реально существующих стран ~250.
# Несуществующие комбинации просто пропускаем (curl --fail вернёт 404).
#
# Использование:
#   cd composeApp && ./fetch-flags.sh
#
# Занимает 1-2 минуты, итоговый размер ~700KB-1MB.

set -uo pipefail

DIR="$(dirname "$0")/src/commonMain/composeResources/files/flags"
mkdir -p "$DIR"
cd "$DIR"

CDN="https://cdn.jsdelivr.net/gh/jdecked/twemoji@15.1.0/assets/72x72"

# A=0x1F1E6 ... Z=0x1F1FF
to_cp() {
    local letter="$1"
    local n=$(($(printf '%d' "'$letter") - 65))   # A→0, B→1, …
    printf '%x' $((0x1F1E6 + n))
}

count=0
skipped=0
total=0
for c1 in {A..Z}; do
    for c2 in {A..Z}; do
        total=$((total + 1))
        iso="${c1}${c2}"
        out="$(echo "$iso" | tr '[:upper:]' '[:lower:]').png"
        if [[ -f "$out" ]]; then
            count=$((count + 1))
            continue
        fi
        cp1="$(to_cp "$c1")"
        cp2="$(to_cp "$c2")"
        url="${CDN}/${cp1}-${cp2}.png"
        if curl -fsSL --max-time 10 -o "$out" "$url" 2>/dev/null; then
            count=$((count + 1))
            printf '\r→ %s (%d ok, %d skip / %d)' "$iso" "$count" "$skipped" "$total"
        else
            skipped=$((skipped + 1))
            rm -f "$out" 2>/dev/null || true
        fi
    done
done

# Глобальный/региональные флаги: используем UN флаг как fallback,
# если eSIM Go отдаст странный «глобальный» псевдо-ISO.
# (Регионы вроде "EU" уже скачались в общем переборе.)

echo
echo "Done. $count flags in: $DIR"
ls "$DIR" | wc -l | xargs -I {} echo "Total files: {}"
du -sh "$DIR"
