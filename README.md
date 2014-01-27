Instru��es de compila��o:
	1) Utilizando MAVEN:
		execute o comando a seguir no diretorio raiz do projeto (onde encontra-se o POM.xml)
			mvn clean install -DskipTests
		ser� gerado um direotorio chamado "target" no diretorio raiz do projeto, nele haver� o diretorio chamado "dist", esse diret�rio contem a aplica��o.
		para executa��o, mova o diretorio "dist" com todo seu conte�do para onde voc� preferir executa-lo, caso n�o queira, n�o existe necessidade de move-lo.
		uma vez no direotiro "dist" haver� mais dois diretorios, um chamado "bin" e outro chamado "lib". no diretorio "lib" contem o jar da aplica��o, no diretorio chamado "bin" contem um arquivo tipo "bat" e um tipo "sh" para rodar em seus respectivos sistemas operacionais.
		para executar, invoque o arquivo "run" (sh se linux bat se windows) passando os parametros como descrito na sess�o seguinte.
		
		ex: 
			run.bat 5 /tmp/p1 /tmp/p2
		OBS: o maven ir� utilizar a internet para baixar um plugin responsavel por gerar o empacotamento executavel desse programa, se a maquina n�o tiver conex�o com a internet, n�o utilize esse metodo.
		
	2) Utilizando Eclipse:
		exporte o projeto no formato JAR.
		determine a classe main
			br.com.nozella.teste.main.PrinterApp
		salve onde achar necessario.
		para executar, siga at� o diretorio onde o projeto foi exportado e invoque o comando como no exemplo abaixo: (onde arquivo.jar � o nome determinado por voc� durante a exporta��o do projeto)
		
		ex:
			java -jar {arquivo.jar} 5 /tmp/p1 /tmp/p2
		

Instru��es de execu��o:

Copie os diret�rios juntamente com seus conteudos do caminho "src/main/resources" para um diret�rio de sua prefer�ncia, por exemplo c:\tmp (windows) ou /tmp (linux). 
Lembre-se que esse diret�rio ser� usado durante a execu��o do programa e far� parte dos parametros de entrada para execu��o do programa;

Considerando que tenha copiado os conte�dos para o diretorio "tmp" na raiz de seu sistema operacional, siga as instru��es da seguinte forma.

Execute a aplica��o passando os parametros na sequencia abaixo:
	1) 5 (um n�mero inteiro que ser� considerado o tamanho da fila);
	2) /tmp/p1 (diretorio onde o primeiro Producer a ser instanciado ficar� ouvindo)(independente de linux ou windows);
	3) /tmp/p2 (diretorio onde o primeiro Producer a ser instanciado ficar� ouvindo)(independente de linux ou windows);
	
	IMPORTANTE
	** passe os parametros com a barra (/) normal
	** caso for rodar o programa no windows, n�o coloque a letra da unidade raiz, copie os arquivos para um local usando a unidade onde encontra-se o sistema operacional

	