package org.dell.kube.pages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/pages")
public class PageController {

    private IPageRepository pageRepository;
    public PageController(IPageRepository pageRepository)
    {
        this.pageRepository = pageRepository;
    }

    @PostMapping
    public ResponseEntity<Page> create(@RequestBody Page page) {
        Page newPage= pageRepository.create(page);
        logger.info("CREATE-INFO:Creating page with id = " + newPage.getId());
        logger.debug("CREATE-DEBUG:Creating page with id = " + newPage.getId());
        return new ResponseEntity<Page>(newPage, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<Page> read(@PathVariable long id) {

        logger.info("READ-INFO:Fetching page with id = " + id);
        logger.debug("READ-DEBUG:Fetching page with id = " + id);

        Page page = pageRepository.read(id);
        if(page!=null)
            return new ResponseEntity<Page>(page, HttpStatus.OK);
        else {
            logger.error("READ-ERROR:Could not find page with id = " + id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Page>> list() {
        List<Page> pages= pageRepository.list();
        logger.info("List-INFO:Listing page with id = " + pages);
        logger.debug("List-DEBUG:Listing page with id = " + pages);
        return new ResponseEntity<List<Page>>(pages, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Page> update(@RequestBody Page page, @PathVariable long id) {
        Page updatedPage= pageRepository.update(page,id);
        logger.info("UPDATE-INFO:Updating page with id = " + id);
        logger.debug("UPDATE-DEBUG:Updating page with id = " + id);
        if(updatedPage!=null)
            return new ResponseEntity<Page>(updatedPage, HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        pageRepository.delete(id);
        logger.info("DELETE-INFO:Deleting page with id = " + id);
        logger.debug("DELETE-DEBUG:Deleting page with id = " + id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    Logger logger =(Logger)LoggerFactory.getLogger(this.getClass());
}
