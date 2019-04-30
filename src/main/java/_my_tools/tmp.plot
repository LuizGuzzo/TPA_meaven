set terminal png
set output "./diccolisoes.png"
set title "Colisões com Função de Hashing polinomial BYTE"
set xlabel "Posições do Vetor"
set ylabel "Colisões"
set style fill solid 0.3
set style data boxes
plot "./dicdadoscolisoes.txt"
