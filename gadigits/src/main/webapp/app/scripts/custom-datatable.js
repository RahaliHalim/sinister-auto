

    $(document).ready(function() {
    // Setup - add a text input to each footer cell
    /*$('#example tfoot th').each( function () {
        var title = $(this).text();
        
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    } );*/
   
    // DataTable
    var table = $('#servrmqs').DataTable();
    $('#myInput').on( 'keyup ', function () {
        console.log("event sharch");
        table.search( this.value ).draw();
    } );
    // Apply the search
    /*table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            console.log("key up change");
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
                    console.log("draw when key up change");
            }
        } );
    } );*/
} );
/*$('table.dataTable tfoot').each(function () {
    $(this).insertAfter($(this).siblings('tbody'));
    });*/
