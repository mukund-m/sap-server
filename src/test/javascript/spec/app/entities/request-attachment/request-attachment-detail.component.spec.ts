import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RequestAttachmentDetailComponent } from '../../../../../../main/webapp/app/entities/request-attachment/request-attachment-detail.component';
import { RequestAttachmentService } from '../../../../../../main/webapp/app/entities/request-attachment/request-attachment.service';
import { RequestAttachment } from '../../../../../../main/webapp/app/entities/request-attachment/request-attachment.model';

describe('Component Tests', () => {

    describe('RequestAttachment Management Detail Component', () => {
        let comp: RequestAttachmentDetailComponent;
        let fixture: ComponentFixture<RequestAttachmentDetailComponent>;
        let service: RequestAttachmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [RequestAttachmentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RequestAttachmentService,
                    JhiEventManager
                ]
            }).overrideTemplate(RequestAttachmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestAttachmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestAttachmentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RequestAttachment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.requestAttachment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
