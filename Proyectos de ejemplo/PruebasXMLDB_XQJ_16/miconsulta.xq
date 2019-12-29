(: Productos con precio > 50 y zona 10 :)
for $prod in collection("/db/BDProductosXML")/productos/produc
where $prod/precio>50 and $prod/cod_zona=10
return $prod 
