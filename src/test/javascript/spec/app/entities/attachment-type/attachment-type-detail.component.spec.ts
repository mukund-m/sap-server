import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AttachmentTypeDetailComponent } from '../../../../../../main/webapp/app/entities/attachment-type/attachment-type-detail.component';
import { AttachmentTypeService } from '../../../../../../main/webapp/app/entities/attachment-type/attachment-type.service';
import { AttachmentType } from '../../../../../../main/webapp/app/entities/attachment-type/attachment-type.model';

describe('Component Tests', () => {

    describe('AttachmentType Management Detail Component', () => {
        let comp: AttachmentTypeDetailComponent;
        let fixture: ComponentFixture<AttachmentTypeDetailComponent>;
        let service: AttachmentTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [AttachmentTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AttachmentTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(AttachmentTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttachmentTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttachmentTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AttachmentType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.attachmentType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
