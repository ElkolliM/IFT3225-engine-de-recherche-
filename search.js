// JavaScript code 
function search_txt() {

    let input = document.getElementById('searchbar').value
    input = input.trim().toLowerCase();
    console.log('input : ' + input);
    if (!isEmpty(input.trim())) {
        getData(input);
    }



}



function getData(input) { //lire le fichier  fourni et envoye les informations a un autre
    var xmlhttp;

    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            var lines = xmlhttp.responseText; //*here we get all lines from text file*

            intoArray(lines, input); //here we call function with parameter "lines*"                   
        }
    }

    xmlhttp.open("GET", "tfidf_out.txt", true);
    xmlhttp.send();
}

function intoArray(lines, input) {
    // splitrer la base de donner a une tableau 

    var lineArr = lines.split('\n');
    var found = 0;
    //console.log('lineArr-->'+lineArr);
    var tableRowContent = '';
    var tableContent = '';


    var queryArr = input.split(' ');

    var tf_query = (1 / queryArr.length);

    console.log('--tf_query--' + tf_query);

    queryArr.forEach(function(input_value, index) {

        console.log('--input_value--' + input_value);

        lineArr.forEach(function(value, i) {

            if (!isEmpty(value.trim())) {
                var subArray = value.trim().split("@@");
                //console.log('subArray'+subArray);
                txtContent = subArray[3].toLowerCase();
                //console.log('txtContent : '+txtContent);

                if (txtContent.includes(input_value)) {
                    found = 1;
                    //console.log('found !! : '+subArray);
                    //File@@document_1.txt@@Term@@Exemple@@tf : 0.0075@@idf : 0.5109@@tfidf : 0.0038

                    // calcule de similariter entre la requette et le fichier output 

                    query_tf_idf = tf_query * subArray[7];

                    console.log('--query_tf_idf--' + query_tf_idf);


                    doc_tf_idf = subArray[9];

                    dot_product = (query_tf_idf * doc_tf_idf) + (query_tf_idf * doc_tf_idf);

                    console.log('--dot_product--' + dot_product);

                    sqrt_query_tf_idf = Math.sqrt((query_tf_idf * query_tf_idf) + (query_tf_idf * query_tf_idf));

                    sqrt_doc_tf_idf = Math.sqrt((doc_tf_idf * doc_tf_idf) + (doc_tf_idf * doc_tf_idf));

                    console.log('--sqrt_query_tf_idf--' + sqrt_query_tf_idf);

                    console.log('--sqrt_doc_tf_idf--' + sqrt_doc_tf_idf);

                    cosine_similarity = 0;

                    if ((sqrt_query_tf_idf * sqrt_doc_tf_idf) > 0) {

                        cosine_similarity = (dot_product / (sqrt_query_tf_idf * sqrt_doc_tf_idf));

                        console.log('--cosine_similarity--' + cosine_similarity);

                    }


                    tableRowContent = tableRowContent + '<tr>' + '<td>' + subArray[1] + '</td>' + '<td>' + subArray[3] + '</td>' + '<td>' + subArray[5] + '</td>' + '<td>' + subArray[7] + '</td>' + '<td>' + subArray[9] + '</td>' + '<td>' + cosine_similarity + '</td>' + '</tr>';
                }
            }
        });

    });

    if (found == 1) {
        tableRowContent = '<table id = "documentList"><tr><th>Document Name</th><th>Text</th><th>Term Frequency(TF)</th><th>Inverse Document Frequency(IDF)</th><th>TF*IDF</th><th>Cosine Similarity</th></tr>' + tableRowContent + '</table>';
        console.log('tableRowContent :' + tableRowContent);
        document.getElementById('displayList').innerHTML = tableRowContent;
    }
    //just to check if it works output lineArr[index] as below
    //document.write(lineArr[2]);         
    //document.write(lineArr[3]);
}


function isEmpty(str) {
    return (!str || 0 === str.length);
}